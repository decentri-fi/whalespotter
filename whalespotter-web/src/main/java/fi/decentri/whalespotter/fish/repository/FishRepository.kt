package fi.decentri.whalespotter.fish.repository

import fi.decentri.whalespotter.fish.data.Fish
import org.springframework.data.jpa.repository.JpaRepository

interface FishRepository : JpaRepository<Fish, String> {
}