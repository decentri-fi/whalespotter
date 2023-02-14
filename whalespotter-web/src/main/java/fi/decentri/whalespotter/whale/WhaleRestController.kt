package fi.decentri.whalespotter.whale

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/whales")
class WhaleRestController(
    private val whaleService: WhaleService
) {
    @GetMapping
    fun getWhales(pageable: Pageable): Page<Whale> {
        return whaleService.getWhales(pageable)
    }
}