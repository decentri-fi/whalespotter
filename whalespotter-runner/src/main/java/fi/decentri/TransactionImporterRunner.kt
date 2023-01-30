package fi.decentri

import com.google.gson.JsonParser
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration
class TransactionImporterRunner(
    private val httpClient: HttpClient,
    @Value("\${ethereum.url}") private val ethereumUrl: String,
) {

    fun run() = runBlocking {
        val request = mapOf(
            "jsonrpc" to "2.0",
            "id" to 0,
            "method" to "alchemy_getAssetTransfers",
            "params" to mapOf(
                "fromAddress" to "0x83A524af3cf8eB146132A2459664f7680A5515bE",
                "excludeZeroValue" to false,
                "category" to listOf(
                    "external",
                    "internal",
                    "erc20",
                    "erc721",
                    "erc1155",
                    "specialnft"
                )
            )
        )

        val response: String = httpClient.post(ethereumUrl) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

        JsonParser.parseString(response)
    }
}