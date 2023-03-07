package fi.decentri.whalespotter.account

import fi.decentri.whalespotter.decentrifi.DecentrifiClient
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountService(
    private val accountRepository: AccountRepository,
) {
    @Transactional
    fun getAccountInfo(owner: String): Account {
        val account = accountRepository.findById(owner)
        return account.orElseGet {
            accountRepository.save(Account(owner))
        }
    }
}