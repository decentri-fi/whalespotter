package fi.decentri.whalespotter.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class WebSecurityConfig {

    @Bean
    fun provideSecurityFilterChain(http: HttpSecurity): DefaultSecurityFilterChain {
        http
            .addFilterBefore(Web3SecurityFilter(), BasicAuthenticationFilter::class.java)
            .authorizeHttpRequests {
                it
                    .requestMatchers(HttpMethod.OPTIONS).permitAll()
                    .requestMatchers("/actuator/**").permitAll()
                    .requestMatchers("/whales").permitAll()
                    .requestMatchers("/sitemap.xml").permitAll()
                    .anyRequest().authenticated()

            }
            .csrf().disable()
        return http.build()
    }

    @Bean
    fun provideCorsConfig(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("https://whalespotter.decentri.fi", "http://localhost:3000")
        configuration.allowedMethods = listOf("GET", "POST", "HEAD", "OPTIONS", "PUT", "DELETE")
        configuration.allowedHeaders = listOf("*")
        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }

}