package fi.decentri.whalespotter.nametag

import kotlinx.coroutines.runBlocking
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/nametags")
class NameTagRestController(
    private val nameTagService: NameTagService
) {

    @GetMapping
    fun getAll(): List<NameTagVO> {
        return nameTagService.getAll()
    }

    @GetMapping("/{address}")
    fun byAdress(@PathVariable("address") address: String) = runBlocking {
        nameTagService.getByAddress(address)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }
}