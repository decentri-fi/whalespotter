package fi.decentri.transaction.service

import fi.decentri.whalespotter.transaction.data.Transaction
import fi.decentri.whalespotter.transaction.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository
) {

    @Transactional(readOnly = true)
    fun contains(txId: String): Boolean {
        return transactionRepository.existsById(txId)
    }

    @Transactional
    fun save(transactionList: List<Transaction>): MutableList<Transaction> {
        val tx = transactionList.distinctBy {
            it.id.lowercase()
        }.filter {
            !contains(it.id)
        }
        return transactionRepository.saveAll(tx)
    }
}