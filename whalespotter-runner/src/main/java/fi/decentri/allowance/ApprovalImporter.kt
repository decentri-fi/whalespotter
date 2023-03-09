package fi.decentri.allowance

import com.google.gson.JsonParser
import com.google.gson.internal.LinkedTreeMap
import fi.decentri.whalespotter.approval.Approval
import fi.decentri.whalespotter.decentrifi.DecentrifiClient
import fi.decentri.whalespotter.decentrifi.domain.DefiEventDTO
import fi.decentri.whalespotter.decentrifi.domain.DefiEventType
import fi.decentri.whalespotter.decentrifi.domain.Network
import fi.decentri.whalespotter.decentrifi.domain.TokenVO
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withTimeout
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import java.math.BigInteger

@Component
class ApprovalImporter(
    private val decentrifiClient: DecentrifiClient,
    private val approvalService: ApprovalService
) {

    val logger = LoggerFactory.getLogger(this::class.java)
    val approvalTopic = "0x8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925"

    @Async
    suspend fun import(it: String) {
        logger.info("Importing approvals for $it")
        Network.values().map { network ->
            val allowances = getAllowancesForNetwork(it.lowercase(), network)
            approvalService.updateApprovals(
                it.lowercase(), allowances, network
            )
        }
    }

    suspend fun getApprovalEvents(
        owner: String,
        network: Network,
        tokens: List<TokenVO>
    ): List<DefiEventDTO> {
        val result = fetchApprovalEventsForTokens(tokens, owner, network)


        val jsonElement = JsonParser.parseString(result).asJsonObject["result"]

        if (jsonElement == null || jsonElement.isJsonNull) {
            logger.info("Unable to parse result for $owner on $network")
            return emptyList()
        }

        val events = jsonElement.asJsonArray.map {
            it.asJsonObject["transactionHash"]
        }

        val semaphore = Semaphore(2)
        return events.flatMap {
            try {
                semaphore.withPermit {
                    withTimeout(5000) {
                        decentrifiClient.getEvents(it.asString, network)
                    }
                }
            } catch (ex: Exception) {
                logger.error("timeout for $it", ex)
                emptyList()
            }
        }.filter {
            it.metadata.containsKey("owner") && (it.metadata["owner"] as String).lowercase() == owner.lowercase()
        }.filter {
            it.type == DefiEventType.APPROVAL
        }
    }

    private suspend fun fetchApprovalEventsForTokens(tokens: List<TokenVO>, user: String, network: Network) =
        decentrifiClient.listenForApprovalLogs(tokens.map {
            it.address
        }, approvalTopic, user, network)

    private suspend fun getAllowancesForNetwork(owner: String, network: Network): List<Approval> {
        try {
            val tokens = decentrifiClient.getTokens(network)
            if (tokens.isEmpty()) {
                logger.info("No tokens found for $network")
                return emptyList()
            }

            return getApprovalEvents(
                owner, network, tokens
            ).mapNotNull { event ->
                event.toAllowance(network)
            }.filter {
                it.amount > BigInteger.ZERO
            }
        } catch (ex: Exception) {
            logger.error("error fetching allowances for $network and owner $owner", ex)
            return emptyList()
        }
    }

    private suspend fun DefiEventDTO.toAllowance(
        network: Network
    ) = try {
        val user = metadata["owner"] as String
        val spender = metadata["spender"] as String
        val asset = (metadata["asset"] as LinkedTreeMap<String, Any>)["address"].toString()
        val amount = decentrifiClient.getAllowance(
            user, spender, asset, network
        )

        Approval(
            owner = user.lowercase(),
            spender = spender.lowercase(),
            amount = amount,
            token = asset,
            network = network
        )
    } catch (ex: Exception) {
        logger.error("Error while importing allowance", ex)
        null
    }
}