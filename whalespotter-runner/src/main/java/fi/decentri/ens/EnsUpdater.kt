package fi.decentri.ens

import fi.decentri.client.DecentrifiClient
import fi.decentri.whalespotter.whale.WhaleRepository
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled

@Configuration
class EnsUpdater(
    private val decentrifiClient: DecentrifiClient,
    private val whaleRepository: WhaleRepository
) {

    private val logger = LoggerFactory.getLogger(this::class.java)
    @Scheduled(fixedDelay = 1000 * 60 * 10)
    fun run() = runBlocking {
        whaleRepository.findAll().forEach {
            try {
                val ens = decentrifiClient.getEns(it.address)
                if (ens.containsKey("name")) {
                    it.ens = ens["name"] as String
                    whaleRepository.save(it)
                }
            } catch (e: Exception) {
                logger.error("Failed to update ens for ${it.address}", e)
            }
        }
    }

}