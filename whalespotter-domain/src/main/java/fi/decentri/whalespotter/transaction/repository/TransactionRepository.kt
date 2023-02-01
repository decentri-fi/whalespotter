package fi.decentri.whalespotter.transaction.repository

import fi.decentri.whalespotter.transaction.data.Transaction
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param

interface TransactionRepository : JpaRepository<Transaction, String> {

    fun findAllByFromOrToOrderByTimeDesc(@Param("from") from: String, @Param("to") to: String): List<Transaction>

}
