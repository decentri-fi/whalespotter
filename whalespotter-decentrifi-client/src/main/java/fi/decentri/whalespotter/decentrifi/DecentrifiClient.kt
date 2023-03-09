package fi.decentri.whalespotter.decentrifi

import fi.decentri.whalespotter.decentrifi.domain.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.springframework.stereotype.Service
import java.math.BigInteger

@Service
class DecentrifiClient(
    private val httpClient: HttpClient
) {

    private val baseUrl = "https://api.decentri.fi"



    suspend fun getERC5511Balance(
        token: String,
        address: String,
        tokenId: String,
        network: Network
    ): BalanceResponse {
        return httpClient.get("$baseUrl/nft/${token}/${address}/balance-of/${tokenId}?network=${network.name}")
            .body()
    }

    suspend fun listenForTransactionLogs(contracts: List<String>, topic: String): String {
        return httpClient.post("$baseUrl/networks/ethereum/events/logs") {
            contentType(ContentType.Application.Json)
            this.setBody(
                GetEventLogsCommand(
                    addresses = contracts,
                    topic = topic,
                    optionalTopics = listOf(
                        "0x00000000000000000000000083a524af3cf8eb146132a2459664f7680a5515be"
                    )
                )
            )
        }.body()
    }

    suspend fun getTokens(network: Network): List<TokenVO> {
        return httpClient.get("$baseUrl/erc20/${network.name}").body()
    }

    suspend fun getClaimables(address: String, protocol: Protocol): List<Claimable> {
        return httpClient.get("$baseUrl/${protocol.slug}/$address/claimables").body()
    }

    suspend fun getEns(address: String): Map<String, String> {
        return httpClient.get("$baseUrl/ens/by-address/${address}").body()
    }

    suspend fun getAvatar(ens: String): Map<String, String> {
        return httpClient.get("$baseUrl/ens/by-name/${ens}/avatar").body()
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

    suspend fun getAllowance(owner: String, spender: String, token: String, network: Network): BigInteger {
        return httpClient.get("$baseUrl/erc20/${network.name}/allowance/$token/$owner/$spender").body()
    }

    suspend fun getProtocols(): List<Protocol> {
        return httpClient.get("$baseUrl/protocols").body()
    }
}