package fi.decentri.whalespotter.account.service

import fi.decentri.whalespotter.account.Account
import fi.decentri.whalespotter.account.AccountRepository
import fi.decentri.whalespotter.importer.WhalespotterImporterClient
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val whalespotterImporterClient: WhalespotterImporterClient
) {
    val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun getAccountInfo(owner: String): Account {
        val account = accountRepository.findById(owner)
        return account.orElseGet {
            runBlocking {
                try {
                    whalespotterImporterClient.doImport(owner.lowercase())
                } catch (e: Exception) {
                    logger.error("something went wrong", e)
                }
            }
            accountRepository.save(Account(owner))
        }
    }
}