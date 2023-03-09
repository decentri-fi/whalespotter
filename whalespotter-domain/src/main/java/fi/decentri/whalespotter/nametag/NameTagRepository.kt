package fi.decentri.whalespotter.nametag

import org.springframework.data.jpa.repository.JpaRepository

interface NameTagRepository : JpaRepository<NameTag, String> {
}