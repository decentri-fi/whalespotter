package fi.decentri.whalespotter.allowance

import fi.decentri.whalespotter.approval.Approval
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/allowance")
class AllowanceRestController(
    private val allowanceService: AllowanceService
) {

    @RequestMapping
    fun getAllowances(@AuthenticationPrincipal principal: Principal): List<Approval> {
        return allowanceService.findAllByOwner(principal.name.lowercase())
    }
}