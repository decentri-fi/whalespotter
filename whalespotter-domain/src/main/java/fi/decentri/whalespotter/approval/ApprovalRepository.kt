package fi.decentri.whalespotter.approval

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param

interface ApprovalRepository : JpaRepository<Approval, Long> {

    fun findAllByOwner(@Param("owner") owner: String): List<Approval>

}