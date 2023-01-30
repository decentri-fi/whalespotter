package fi.decentri.transaction.cron

import com.google.gson.JsonParser
import fi.decentri.client.DecentrifiClient
import fi.decentri.whalespotter.network.Network
import fi.decentri.whalespotter.transaction.data.Transaction
import fi.decentri.whalespotter.transaction.repository.TransactionRepository
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import jakarta.annotation.PostConstruct
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class TransactionImporterRunner(
    private val httpClient: HttpClient,
    private val transactionRepository: TransactionRepository,
    private val decentrifiClient: DecentrifiClient,
    @Value("\${ethereum.url}") private val ethereumUrl: String,
) {

    val logger = LoggerFactory.getLogger(this::class.java)

    @PostConstruct
    fun run() = runBlocking {
        val request = mapOf(
            "jsonrpc" to "2.0", "id" to 0, "method" to "alchemy_getAssetTransfers", "params" to mapOf(
                "fromAddress" to "0x83A524af3cf8eB146132A2459664f7680A5515bE",
                "excludeZeroValue" to false,
                "category" to listOf(
                    "external", "internal", "erc20", "erc721", "erc1155", "specialnft"
                )
            )
        )

        val response: String = httpClient.post(ethereumUrl) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

        val transactionHashes =
            JsonParser.parseString(response).asJsonObject["result"].asJsonObject["transfers"].asJsonArray.map {
                return@map it.asJsonObject["hash"].asString
            }


        val savedTransactions = transactionHashes.map {
            val tx = decentrifiClient.getTransaction(it, Network.ETHEREUM)
            tx?.let { txVO ->
                if(transactionRepository.findById(txVO.hash).isEmpty) {
                    transactionRepository.save(
                        Transaction(
                            id = txVO.hash,
                            network = Network.ETHEREUM,
                            from = txVO.from,
                            to = txVO.to,
                            block = txVO.blockNumber.toString(),
                            time = Date(),
                            value = txVO.value.toString()
                        )
                    )
                }
            }
        }
        logger.info("Saved ${savedTransactions.size} transactions")
    }
}