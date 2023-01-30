package fi.decentri.whalespotter.fish.service

import fi.decentri.whalespotter.fish.command.AddFishCommand
import fi.decentri.whalespotter.fish.data.Fish
import fi.decentri.whalespotter.fish.repo.FishRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class FishService(
    private val fishRepository: FishRepository
) {

    @Transactional(readOnly = true)
    fun getFish(owner: String): List<Fish> {
        return fishRepository.findAllByOwner(owner)
    }

    @Transactional
    fun addFish(addFishCommand: AddFishCommand, owner: String): Fish {
        return fishRepository.save(
            Fish(
                address = addFishCommand.address,
                id = UUID.randomUUID().toString(),
                owner = owner
            )
        )
    }
}