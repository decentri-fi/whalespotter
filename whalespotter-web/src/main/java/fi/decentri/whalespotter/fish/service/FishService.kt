package fi.decentri.whalespotter.fish.service

import fi.decentri.whalespotter.fish.command.AddFishCommand
import fi.decentri.whalespotter.fish.data.Fish
import fi.decentri.whalespotter.fish.repository.FishRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class FishService(
    private val fishRepository: FishRepository
) {

    @Transactional(readOnly = true)

    fun getFish(): List<Fish> {
        return fishRepository.findAll()
    }

    fun addFish(addFishCommand: AddFishCommand): Fish {
        return fishRepository.save(
            Fish(
                address = addFishCommand.address,
                network = addFishCommand.network,
                id = UUID.randomUUID().toString(),
                owner = "owner"
            )
        )
    }
}