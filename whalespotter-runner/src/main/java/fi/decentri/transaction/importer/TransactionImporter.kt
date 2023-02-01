package fi.decentri.transaction.importer

import fi.decentri.alchemy.AlchemyClient
import fi.decentri.client.DecentrifiClient
import fi.decentri.transaction.service.TransactionService
import fi.decentri.whalespotter.network.Network
import fi.decentri.whalespotter.transaction.data.Transaction
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class TransactionImporter(
    private val transactionService: TransactionService,
    private val decentrifiClient: DecentrifiClient,
    private val alchemyClient: AlchemyClient
) {

    val logger = LoggerFactory.getLogger(this::class.java)

    suspend fun import(user: String) = coroutineScope {
        val semaphore = Semaphore(10)
        val transactionHashes = alchemyClient.getTransactions(user)
        val savedTransactions = transactionHashes
            .filter {
                !transactionService.contains(it)
            }
            .map {
                async {
                    semaphore.withPermit {
                        decentrifiClient.getTransaction(it, Network.ETHEREUM)
                    }
                }
            }.awaitAll()
            .filterNotNull()
            .map { txVO ->
                Transaction(
                    id = txVO.hash,
                    network = Network.ETHEREUM,
                    from = txVO.from,
                    to = txVO.to,
                    block = txVO.blockNumber.toString(),
                    time = Date(txVO.time * 1000),
                    value = txVO.value.toString()
                )
            }

        val transactions = transactionService.save(savedTransactions)
        logger.info("Saved ${savedTransactions.size} transactions")
        return@coroutineScope transactions
    }
}