package fi.decentri.client

import fi.decentri.client.domain.Claimable
import fi.decentri.client.domain.Protocol
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import org.springframework.stereotype.Service

@Service
class DecentrifiClient(
    private val httpClient: HttpClient
) {

    private val baseUrl = "https://api.decentri.fi"

    suspend fun getClaimables(address: String, protocol: Protocol): List<Claimable> {
        return httpClient.get("$baseUrl/${protocol.slug}/$address/claimables").body()
    }

    suspend fun getProtocols(): List<Protocol> {
        return httpClient.get("$baseUrl/protocols").body()
    }
}