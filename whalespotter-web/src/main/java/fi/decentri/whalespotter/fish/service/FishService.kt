package fi.decentri.whalespotter.fish.service

import fi.decentri.whalespotter.fish.command.AddFishCommand
import fi.decentri.whalespotter.fish.data.Fish
import fi.decentri.whalespotter.fish.repo.FishRepository
import fi.decentri.whalespotter.importer.WhalespotterImporterClient
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class FishService(
    private val fishRepository: FishRepository,
    private val whalespotterImporterClient: WhalespotterImporterClient
) {

    @Transactional(readOnly = true)
    fun getFish(owner: String): List<Fish> {
        return fishRepository.findAllByOwner(owner)
    }

    @Transactional
    fun addFish(addFishCommand: AddFishCommand, owner: String): Fish {
        if (fishRepository.findAllByOwner(owner).any {
                it.address == addFishCommand.address
            }) {
            throw IllegalArgumentException("Fish with address ${addFishCommand.address} already tracked")
        }
        val fish = fishRepository.save(
            Fish(
                address = addFishCommand.address,
                owner = owner
            )
        )
        runBlocking {
            whalespotterImporterClient.doImport(fish.address)
        }
        return fish
    }
}