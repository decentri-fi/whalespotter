package fi.decentri.approval.cron

import fi.decentri.approval.ApprovalImporter
import fi.decentri.whalespotter.fish.repo.FishRepository
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@Configuration
class AllowanceImportCron(
    private val approvalImporter: ApprovalImporter,
    private val fishRepository: FishRepository
) {

    val logger = LoggerFactory.getLogger(this::class.java)

    @OptIn(ExperimentalTime::class)
    @Scheduled(fixedDelay = 1000 * 60 * 60) //every hour
    fun importTransactions() = runBlocking {
        val passed = measureTime {
            fishRepository.findAll().distinctBy {
                it.address.lowercase()
            }.forEach {
                approvalImporter.import(it.address.lowercase())
            }
        }
        logger.info("Imported approvals in ${passed.inWholeSeconds} seconds")
    }
}