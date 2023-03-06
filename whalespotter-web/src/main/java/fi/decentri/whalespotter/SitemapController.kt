package fi.decentri.whalespotter

import fi.decentri.whalespotter.decentrifi.DecentrifiClient
import fi.decentri.whalespotter.whale.WhaleRepository
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SitemapController(
    private val decentrifiClient: DecentrifiClient,
    private val whaleRepository: WhaleRepository
) {
    @GetMapping("/sitemap.xml", produces = ["application/xml"])
    fun getSitemap(): String = runBlocking {
        """<?xml version="1.0" encoding="UTF-8"?>
            <urlset xmlns="http://www.sitemaps.org/schemas/sitemap/0.9">
                ${url("https://decentri.fi")}
                ${url("https://decentri.fi/explore")}
                ${url("https://decentri.fi/protocols")}
                ${getProtocols()}
                ${getWhales()}
            </urlset>
        """.trimIndent()
    }

    fun url(loc: String, changefreq: String = "monthly"): String {
        return """
            <url>
            <loc>$loc</loc>
            <changefreq>$changefreq</changefreq>
            </url>
        """.trimIndent()
    }

    suspend fun getProtocols(): String {
        return decentrifiClient.getProtocols().map {
            url("https://decentri.fi/protocols/${it.slug}")
        }.joinToString("\n")
    }

    suspend fun getWhales(): String {
        return whaleRepository.findAll().map {
            url("https://decentri.fi/${it.address}/profile")
            url("https://decentri.fi/${it.address}/claimables")
        }.joinToString("\n")
    }

}