package fi.decentri.alchemy

import com.google.gson.JsonParser
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class AlchemyClient(
    private val httpClient: HttpClient,
    @Value("\${ethereum.url}") private val ethereumUrl: String,
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

        val response: String = httpClient.post(ethereumUrl) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

        return JsonParser.parseString(response).asJsonObject["result"].asJsonObject["transfers"].asJsonArray.map {
            return@map it.asJsonObject["hash"].asString
        }
    }
}