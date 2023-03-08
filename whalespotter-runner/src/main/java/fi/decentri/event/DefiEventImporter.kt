package fi.decentri.event

import com.google.gson.Gson
import fi.decentri.decenrifi.DecentrifiClient
import fi.decentri.whalespotter.event.DefiEvent
import fi.decentri.whalespotter.event.DefiEventRepository
import fi.decentri.whalespotter.transaction.data.Transaction
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*

@Component
class DefiEventImporter(
    private val defiEventRepository: DefiEventRepository,
    private val decentrifiClient: DecentrifiClient
) {

    val logger = LoggerFactory.getLogger(this::class.java)
    val gson = Gson()

    suspend fun import(transaction: Transaction) {
        val events = decentrifiClient.getEvents(
            transaction.id,
            transaction.network
        ).map {
            try {
                val event = DefiEvent(
                    id = UUID.randomUUID().toString(),
                    type = it.type,
                    transaction = transaction,
                    protocol = it.protocol,
                    metadata = it.metadata
                )
                defiEventRepository.save(event)
            } catch (ex: Exception) {
                logger.error("Error importing event ${it.type} for transaction ${transaction.id}")
            }
        }
        logger.debug("Saved ${events.size} events for transaction ${transaction.id}")
    }
}