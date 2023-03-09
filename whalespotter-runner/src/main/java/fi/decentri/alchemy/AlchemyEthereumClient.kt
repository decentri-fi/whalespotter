package fi.decentri.alchemy

import fi.decentri.whalespotter.decentrifi.domain.Network
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AlchemyEthereumClient(
    httpClient: HttpClient,
    @Value("\${ethereum.url}") private val ethereumUrl: String,
) : AlchemyClient(
    ethereumUrl, httpClient, Network.ETHEREUM
)