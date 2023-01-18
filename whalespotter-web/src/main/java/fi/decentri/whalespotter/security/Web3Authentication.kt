package fi.decentri.whalespotter.security

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

class Web3Authentication(
    private val name: String
) : AbstractAuthenticationToken(
    emptyList()
) {
    override fun getName(): String {
        return name
    }

    override fun getCredentials(): Any {
        return name
    }

    override fun isAuthenticated(): Boolean {
        return true
    }

    override fun getPrincipal(): Any {
        return name
    }
}