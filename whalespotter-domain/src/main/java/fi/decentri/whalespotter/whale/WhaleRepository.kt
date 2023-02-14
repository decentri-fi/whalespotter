package fi.decentri.whalespotter.whale

import org.springframework.data.jpa.repository.JpaRepository

interface WhaleRepository : JpaRepository<Whale, Long>