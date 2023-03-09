package fi.decentri.allowance

import fi.decentri.whalespotter.approval.Approval
import fi.decentri.whalespotter.approval.ApprovalRepository
import fi.decentri.whalespotter.decentrifi.domain.Network
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ApprovalService(
    private val approvalRepository: ApprovalRepository
) {

    val logger = LoggerFactory.getLogger(this::class.java)

    @Transactional
    fun updateApprovals(owner: String, approvals: List<Approval>, network: Network) {
        val previousApprovals = approvalRepository.findAllByOwner(owner).filter {
            it.network == network
        }

        val newApprovals = approvals.filter { approval ->
            previousApprovals.none { it.owner == approval.owner && it.spender == approval.spender }
        }

        val droppedApprovals = previousApprovals.filter { approval ->
            approvals.none { it.owner == approval.owner && it.spender == approval.spender }
        }

        approvalRepository.deleteAll(droppedApprovals)
        approvalRepository.saveAll(newApprovals)

        if (droppedApprovals.isNotEmpty() || newApprovals.isNotEmpty()) {
            logger.info("Updated approvals for $owner on $network: ${newApprovals.size} new, ${droppedApprovals.size} dropped")

        }
    }
}