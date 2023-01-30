package fi.decentri

import com.fasterxml.jackson.module.kotlin.jsonMapper
import io.ktor.client.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

import io.ktor.client.engine.okhttp.*
import io.ktor.serialization.gson.*

@Configuration
class HttpClientConfig {

    @Bean
    fun provideClient(): HttpClient {
        return HttpClient(OkHttp) {
            install(ContentNegotiation) {
                gson()
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 120000
                connectTimeoutMillis = 60000
                socketTimeoutMillis = 120000
            }
        }
    }
}