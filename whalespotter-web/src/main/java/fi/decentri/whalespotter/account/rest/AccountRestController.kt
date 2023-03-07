package fi.decentri.whalespotter.account.rest

import fi.decentri.whalespotter.account.Account
import fi.decentri.whalespotter.account.service.AccountService
import fi.decentri.whalespotter.account.vo.AccountVO
import fi.decentri.whalespotter.membership.MembershipService
import kotlinx.coroutines.runBlocking
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/account")
class AccountRestController(
    private val accountService: AccountService,
    private val membershipService: MembershipService
) {

    @GetMapping
    fun getAccountInfo(@AuthenticationPrincipal principal: Principal): AccountVO {
        return accountService.getAccountInfo(principal.name).toVO()
    }

    fun Account.toVO(): AccountVO {
        return runBlocking {
            AccountVO(
                id = id!!,
                member = membershipService.isMember(id!!)
            )
        }
    }
}