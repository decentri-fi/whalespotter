package fi.decentri.whalespotter.event

import org.springframework.data.jpa.repository.JpaRepository

interface DefiEventRepository: JpaRepository<DefiEvent, String>  {

    fun findAllByTransactionFromOrTransactionToOrderByTransactionTimeDesc(from: String, to: String): List<DefiEvent>

}