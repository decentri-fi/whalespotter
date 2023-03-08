package fi.decentri.whalespotter.importer

import io.ktor.client.*
import io.ktor.client.request.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class WhalespotterImporterClient(
    private val httpClient: HttpClient,
    @Value("\${whalespotter.runner.url}") private val whalespotterRunnerUrl: String
) {

    suspend fun doImport(user: String) {
        httpClient.post("${whalespotterRunnerUrl}/import/$user")
    }
}