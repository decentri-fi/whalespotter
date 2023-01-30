package fi.decentri.whalespotter.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class Web3SecurityFilter : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val username = request.getHeader("owner")
        if (username != null) {
            SecurityContextHolder.getContext().authentication = Web3Authentication(username)
        }

        filterChain.doFilter(request, response)
    }
}