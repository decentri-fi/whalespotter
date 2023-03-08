package fi.decentri.whalespotter.allowance

import fi.decentri.whalespotter.approval.Approval
import fi.decentri.whalespotter.approval.ApprovalRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AllowanceService(private val approvalRepository: ApprovalRepository) {

    @Transactional(readOnly = true)
    fun findAllByOwner(owner: String): List<Approval> {
        return approvalRepository.findAllByOwner(owner)
    }
}