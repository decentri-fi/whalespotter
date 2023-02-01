package fi.decentri.transaction.service

import fi.decentri.whalespotter.transaction.data.Transaction
import fi.decentri.whalespotter.transaction.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TransactionService(
    private val transactionRepository: TransactionRepository
) {

    @Transactional
    fun save(transactionList: List<Transaction>) {
        transactionList.distinctBy {
            it.id.lowercase()
        }.forEach { transaction ->
            if (transactionRepository.findById(transaction.id).isEmpty) {
                transactionRepository.save(
                    transaction
                )
            }
        }
    }
}