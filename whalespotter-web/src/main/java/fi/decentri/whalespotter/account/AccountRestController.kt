package fi.decentri.whalespotter.account

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/account")
class AccountRestController(
    private val accountService: AccountService
) {

    @GetMapping
    fun getAccountInfo(@AuthenticationPrincipal principal: Principal): AccountVO {
        return accountService.getAccountInfo(principal.name).toVO()
    }

    fun Account.toVO(): AccountVO {
        return AccountVO(
            id = id!!
        )
    }
}