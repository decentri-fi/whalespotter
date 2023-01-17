package fi.decentri.whalespotter.fish

import fi.decentri.whalespotter.fish.command.AddFishCommand
import fi.decentri.whalespotter.fish.data.Fish
import fi.decentri.whalespotter.fish.service.FishService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/fish")
class FishController(
    private val fishService: FishService
) {

    @GetMapping
    fun getFishes(): List<Fish> {
        return fishService.getFish()
    }

    @PutMapping
    fun addFish(@RequestBody addFishCommand: AddFishCommand): Fish {
        return fishService.addFish(addFishCommand)
    }
}