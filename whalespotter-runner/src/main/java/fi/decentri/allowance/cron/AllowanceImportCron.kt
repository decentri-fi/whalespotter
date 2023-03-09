package fi.decentri.allowance.cron

import fi.decentri.allowance.ApprovalImporter
import fi.decentri.whalespotter.account.AccountRepository
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@Configuration
class AllowanceImportCron(
    private val approvalImporter: ApprovalImporter,
    private val accountRepository: AccountRepository
) {

    val logger = LoggerFactory.getLogger(this::class.java)

    @OptIn(ExperimentalTime::class)
    @Scheduled(fixedDelay = 1000 * 60 * 60) //every hour
    fun importTransactions() = runBlocking {
        val passed = measureTime {
            accountRepository.findAll().distinctBy {
                it.id!!.lowercase()
            }.forEach {
                approvalImporter.import(it.id!!.lowercase())
            }
        }
        logger.info("Imported approvals in ${passed.inWholeSeconds} seconds")
    }
}