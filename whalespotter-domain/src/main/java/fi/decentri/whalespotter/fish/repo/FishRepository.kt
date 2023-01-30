package fi.decentri.whalespotter.fish.repo

import fi.decentri.whalespotter.fish.data.Fish
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.query.Param

interface FishRepository : JpaRepository<Fish, String> {
    fun findAllByOwner(@Param("owner") owner: String): List<Fish>
}