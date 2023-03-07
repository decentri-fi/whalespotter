package fi.decentri.whalespotter.account

import fi.decentri.whalespotter.decentrifi.DecentrifiClient
import fi.decentri.whalespotter.network.Network
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
    private val decentrifiClient: DecentrifiClient
) {

    @GetMapping
    fun getAccountInfo(@AuthenticationPrincipal principal: Principal): AccountVO {
        return accountService.getAccountInfo(principal.name).toVO()
    }

    fun Account.toVO(): AccountVO {
        return runBlocking {
            AccountVO(
                id = id!!,
                member = decentrifiClient.getERC5511Balance(
                    "0x2953399124f0cbb46d2cbacd8a89cf0599974963",
                    id!!,
                    "59544766117376618043978085178500018507573765124387247653177172736636843196516",
                    network = Network.POLYGON
                ).balance > 0
            )
        }
    }
}