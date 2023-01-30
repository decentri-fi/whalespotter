package fi.decentri.whalespotter.transaction

import fi.decentri.whalespotter.transaction.data.Transaction
import fi.decentri.whalespotter.transaction.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TransactionService(private val transactionRepository: TransactionRepository) {

    @Transactional(readOnly = true)
    fun getAll(owner: String): List<Transaction> {
        return transactionRepository.findAllByFromOrTo(owner, owner)
    }
}