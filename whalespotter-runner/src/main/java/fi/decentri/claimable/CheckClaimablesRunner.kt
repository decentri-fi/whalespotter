package fi.decentri.claimable

import fi.decentri.client.DecentrifiClient
import fi.decentri.whalespotter.fish.repo.FishRepository
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@Configuration
@EnableScheduling
class CheckClaimablesRunner(
    private val fishRepository: FishRepository,
    private val decentrifiClient: DecentrifiClient
) {

    val logger = LoggerFactory.getLogger(this::class.java)

  //  @Scheduled(fixedDelay = 10000)
    fun init() = runBlocking {
        logger.info("starting batch to find claimables")
        val allProtocols = decentrifiClient.getProtocols()
        fishRepository.findAll().forEach { fish ->
            val claimables = allProtocols.flatMap {
                decentrifiClient.getClaimables(fish.address, it)
            }

            logger.info("Fish ${fish.address} has \$${claimables.sumOf { it.dollarValue }} to claim")
        }
        logger.info("done searching for claimables")
    }
}