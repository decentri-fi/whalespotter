package fi.decentri.whalespotter.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import java.security.Principal

class Web3Authentication(
    private val name: String
) : AbstractAuthenticationToken(
    emptyList()
) {
    override fun getName(): String {
        return name.lowercase()
    }

    override fun getCredentials(): Any {
        return name.lowercase()
    }

    override fun isAuthenticated(): Boolean {
        return true
    }

    override fun getPrincipal(): Any {
        return Principal {
            name.lowercase()
        }
    }
}