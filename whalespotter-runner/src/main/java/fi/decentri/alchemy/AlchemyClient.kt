package fi.decentri.alchemy

import com.google.gson.JsonParser
import fi.decentri.whalespotter.network.Network
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*

abstract class AlchemyClient(
    private val url: String,
    private val httpClient: HttpClient,
    val network: Network
) {

    suspend fun getTransactions(user: String): List<String> {
        val request = mapOf(
            "jsonrpc" to "2.0", "id" to 0, "method" to "alchemy_getAssetTransfers", "params" to mapOf(
                "fromAddress" to user,
                "excludeZeroValue" to false,
                "category" to listOf(
                    "external", "internal", "erc20", "erc721", "erc1155", "specialnft"
                )
            )
        )

        val response: String = httpClient.post(url) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

        return JsonParser.parseString(response).asJsonObject["result"].asJsonObject["transfers"].asJsonArray.map {
            return@map it.asJsonObject["hash"].asString
        }
    }
}