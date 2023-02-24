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
        whaleRepository.findAll().forEach { whale ->
            try {
                val ens = decentrifiClient.getEns(whale.address)
                if (ens.containsKey("name")) {
                    whale.ens = ens["name"] as String
                    try {
                        decentrifiClient.getAvatar(whale.ens).let {
                            if (it.containsKey("avatar")) {
                                it["avatar"]?.let { avatar ->
                                    whale.logo = avatar
                                }
                            }
                        }
                    } catch (ex: Exception) {
                        logger.error("Error getting avatar for ${whale.ens}", ex)
                    }
                    whaleRepository.save(whale)
                }
            } catch (e: Exception) {
                logger.error("Failed to update ens for ${whale.address}", e)
            }
        }
    }

}