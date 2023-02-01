package fi.decentri.transaction.cron

import fi.decentri.transaction.importer.TransactionImporter
import fi.decentri.whalespotter.fish.repo.FishRepository
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@Configuration
class TransactionImportCron(
    private val transactionImporter: TransactionImporter,
    private val fishRepository: FishRepository
) {

    val logger = LoggerFactory.getLogger(this::class.java)

    @OptIn(ExperimentalTime::class)
    @Scheduled(fixedDelay = 1000 * 60 * 10)
    fun importTransactions() = runBlocking {
        val passed = measureTime {
            fishRepository.findAll().forEach {
                transactionImporter.import(it.address)
            }
        }
        logger.info("Imported transactions in ${passed.inWholeSeconds} seconds")
    }


}