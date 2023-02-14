package fi.decentri.transaction.importer

import fi.decentri.alchemy.AlchemyClient
import fi.decentri.client.DecentrifiClient
import fi.decentri.event.DefiEventImporter
import fi.decentri.transaction.service.TransactionService
import fi.decentri.whalespotter.transaction.data.Transaction
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Async
import java.util.*

@Configuration
class TransactionImporter(
    private val transactionService: TransactionService,
    private val decentrifiClient: DecentrifiClient,
    private val alchemyClients: List<AlchemyClient>,
    private val defiEventImporter: DefiEventImporter
) {

    val logger = LoggerFactory.getLogger(this::class.java)

    @Async
    suspend fun import(user: String) = coroutineScope {
        val semaphore = Semaphore(10)

        alchemyClients.map {
            it.network to it.getTransactions(user)
        }.forEach { entry ->
            val transactionHashes = entry.second
            val savedTransactions = transactionHashes
                .filter {
                    !transactionService.contains(it)
                }
                .map {
                    async {
                        semaphore.withPermit {
                            decentrifiClient.getTransaction(it, entry.first)
                        }
                    }
                }.awaitAll()
                .filterNotNull()
                .map { txVO ->
                    Transaction(
                        id = txVO.hash,
                        network = entry.first,
                        from = txVO.from,
                        to = txVO.to,
                        block = txVO.blockNumber.toString(),
                        time = Date(txVO.time * 1000),
                        value = txVO.value.toString()
                    )
                }

            val transactions = transactionService.save(savedTransactions)
            transactions.forEach {
                defiEventImporter.import(it)
            }
            logger.info("Saved ${transactions.size} transactions")
        }
    }
}