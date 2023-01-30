package fi.decentri.whalespotter.transaction

import fi.decentri.whalespotter.transaction.data.Transaction
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/transactions")
class TransactionRestController(
    private val transactionService: TransactionService
) {
    @GetMapping
    fun getTransactions(@AuthenticationPrincipal principal: Principal): List<Transaction> {
        return transactionService.getAll(principal.name)
    }
}