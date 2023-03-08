package fi.decentri.user

import fi.decentri.allowance.ApprovalImporter
import fi.decentri.transaction.importer.TransactionImporter
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserImportRestController(
    private val transactionImporter: TransactionImporter,
    private val approvalImporter: ApprovalImporter
) {

    @PostMapping("/import/{userAddress}")
    fun import(@PathVariable("userAddress") userId: String) = runBlocking {
        transactionImporter.import(userId)
        approvalImporter.import(userId)
        ResponseEntity.ok()
    }
}