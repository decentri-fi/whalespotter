package fi.decentri.whalespotter.data

import fi.decentri.whalespotter.fish.data.Fish
import fi.decentri.whalespotter.fish.repo.FishRepository
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.util.*

@Configuration
@Profile("dev")
class DataPopulator(private val fishRepository: FishRepository) {

    @PostConstruct
    fun init() {
        fishRepository.save(
            Fish(
                id = UUID.randomUUID().toString(),
                address = "0x83A524af3cf8eB146132A2459664f7680A5515bE",
                owner = "qds"
            )
        )
    }

}