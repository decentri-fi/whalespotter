package fi.decentri.approval

import com.google.gson.JsonParser
import com.google.gson.internal.LinkedTreeMap
import fi.decentri.decenrifi.DecentrifiClient
import fi.decentri.decenrifi.domain.TokenVO
import fi.decentri.event.DefiEventDTO
import fi.decentri.whalespotter.approval.Approval
import fi.decentri.whalespotter.event.DefiEventType
import fi.decentri.whalespotter.fish.repo.FishRepository
import fi.decentri.whalespotter.network.Network
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
    private val approvalService: ApprovalService,
    private val fishRepository: FishRepository
) {

    val logger = LoggerFactory.getLogger(this::class.java)
    val approvalTopic = "0x8c5be1e5ebec7d5bd14f71427d1e84f3dd0314c0f7b2291e5b200ac8c7c3b925"

    private fun getFishAdresses() = fishRepository.findAll().map {
        it.address.lowercase()
    }

    @Async
    suspend fun import(it: String) {
        logger.info("Importing approvals for $it")
        Network.values().map { network ->
            importForNetwork(it.lowercase(), network)
        }
    }

    suspend fun getApprovalEvents(
        owner: String,
        network: Network,
        tokens: List<TokenVO>
    ): List<DefiEventDTO> {
        val result = decentrifiClient.listenForTransactionLogs(tokens.map {
            it.address
        }, approvalTopic)

        val events = JsonParser.parseString(result).asJsonObject["result"].asJsonArray.map {
            it.asJsonObject["transactionHash"]
        }

        val semaphore = Semaphore(2)
        return events.flatMap {
            try {
                semaphore.withPermit {
                    withTimeout(2000) {
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

    private suspend fun importForNetwork(owner: String, network: Network) {
        try {
            val tokens = decentrifiClient.getTokens(network)
            if (tokens.isEmpty()) {
                logger.info("No tokens found for $network")
                return
            }

            val allowances = getApprovalEvents(
                owner, network, tokens
            ).mapNotNull { event ->
                toAllowance(event, network)
            }

            approvalService.updateApprovals(
                owner, allowances, network
            )
        } catch (ex: Exception) {
            logger.error("error importing approvals for $network", ex)
        }
    }

    private suspend fun toAllowance(
        event: DefiEventDTO,
        network: Network
    ) = try {
        val user = event.metadata["owner"] as String
        val spender = event.metadata["spender"] as String
        val asset = (event.metadata["asset"] as LinkedTreeMap<String, Any>)["address"].toString()
        val amount = decentrifiClient.getAllowance(
            user, spender, asset, network
        )

        if (amount > BigInteger.ZERO) {
            Approval(
                owner = user.lowercase(),
                spender = spender.lowercase(),
                amount = amount,
                token = asset,
                network = network
            )
        } else {
            null
        }
    } catch (ex: Exception) {
        logger.error("Error while importing allowance", ex)
        null
    }
}