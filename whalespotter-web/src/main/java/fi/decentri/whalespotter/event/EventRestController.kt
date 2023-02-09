package fi.decentri.whalespotter.event

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/events")
class EventRestController(
    private val defiEventRepository: DefiEventRepository
) {

    @GetMapping("/{address}")
    fun getEvents(@PathVariable address: String): List<DefiEvent> {
        return defiEventRepository.findAllByTransactionFromOrTransactionToOrderByTransactionTimeDesc(address.lowercase(), address.lowercase())
    }
}