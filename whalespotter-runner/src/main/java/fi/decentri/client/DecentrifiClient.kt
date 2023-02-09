package fi.decentri.client

import fi.decentri.client.domain.Claimable
import fi.decentri.client.domain.Protocol
import fi.decentri.client.domain.TransactionVO
import fi.decentri.event.DefiEventDTO
import fi.decentri.whalespotter.network.Network
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.springframework.stereotype.Service

@Service
class DecentrifiClient(
    private val httpClient: HttpClient
) {

    private val baseUrl = "https://api.decentri.fi"

    suspend fun getClaimables(address: String, protocol: Protocol): List<Claimable> {
        return httpClient.get("$baseUrl/${protocol.slug}/$address/claimables").body()
    }

    suspend fun getTransaction(txId: String, network: Network): TransactionVO? {
        val retVal = httpClient.get("$baseUrl/networks/${network.slug}/tx/$txId")
        return if (retVal.status.isSuccess()) {
            retVal.body()
        } else {
            null
        }
    }

    suspend fun getEvents(txId: String, network: Network): List<DefiEventDTO> {
        return httpClient.get("$baseUrl/events/decode/$txId?network=${network.name}").body()
    }

    suspend fun getProtocols(): List<Protocol> {
        return httpClient.get("$baseUrl/protocols").body()
    }
}