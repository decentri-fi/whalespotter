package fi.decentri.whalespotter.fish

import fi.decentri.whalespotter.fish.command.AddFishCommand
import fi.decentri.whalespotter.fish.data.Fish
import fi.decentri.whalespotter.fish.service.FishService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/fish")
class FishController(
    private val fishService: FishService
) {

    @GetMapping
    fun getFishes(@AuthenticationPrincipal principal: Principal): List<Fish> {
        return fishService.getFish(principal.name)
    }

    @PutMapping(consumes = ["application/json"], produces = ["application/json"])
    fun addFish(
        @RequestBody addFishCommand: AddFishCommand,
        @AuthenticationPrincipal principal: Principal
    ): Fish {
        return fishService.addFish(
            addFishCommand,
            principal.name
        )
    }
}