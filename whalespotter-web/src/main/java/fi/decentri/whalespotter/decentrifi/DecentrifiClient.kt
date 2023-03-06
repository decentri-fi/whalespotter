package fi.decentri.whalespotter.decentrifi

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.springframework.stereotype.Service

@Service
class DecentrifiClient(
    private val httpClient: HttpClient
) {

    private val baseUrl = "https://api.decentri.fi"

    suspend fun getProtocols(): List<Protocol> {
        return httpClient.get("$baseUrl/protocols").body()
    }
}