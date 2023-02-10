package fi.decentri.transaction

import fi.decentri.transaction.importer.TransactionImporter
import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/transactions")
class TransactionImportRestController(private val transactionImporter: TransactionImporter) {

    @PostMapping("/import/{userId}")
    fun import(@PathVariable("userId") userId: String) = runBlocking {
        transactionImporter.import(userId)
        ResponseEntity.ok()
    }
}