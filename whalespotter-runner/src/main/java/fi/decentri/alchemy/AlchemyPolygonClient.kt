package fi.decentri.alchemy

import fi.decentri.whalespotter.decentrifi.domain.Network
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AlchemyPolygonClient(
    httpClient: HttpClient,
    @Value("\${polygon.url}") private val polygonUrl: String,
) : AlchemyClient(
    polygonUrl, httpClient, Network.POLYGON
)