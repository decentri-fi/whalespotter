package fi.decentri.whalespotter.decentrifi

import fi.decentri.whalespotter.decentrifi.domain.BalanceResponse
import fi.decentri.whalespotter.decentrifi.domain.Protocol
import fi.decentri.whalespotter.network.Network
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

    suspend fun getERC5511Balance(
        token: String,
        address: String,
        tokenId: String,
        network: Network
    ): BalanceResponse {
        val result = httpClient.get("$baseUrl/nft/${token}/${address}/balance-of/${tokenId}?network=${network.name}")
            .body<BalanceResponse>()
        return result
    }
}