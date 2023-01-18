package fi.decentri.whalespotter.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@Configuration
class WebSecurityConfig {

    @Bean
    fun provideSecurityFilterChain(http: HttpSecurity): DefaultSecurityFilterChain {
        http.addFilterAfter(Web3SecurityFilter(), BasicAuthenticationFilter::class.java)
        http.authorizeHttpRequests {
            it.requestMatchers("/**").authenticated()
        }
        return http.build()
    }

}