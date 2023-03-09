package fi.decentri.whalespotter.nametag

import fi.decentri.whalespotter.decentrifi.domain.Network
import jakarta.persistence.*

@Entity
@Table(name = "name_tags")
class NameTag(
    @Id
    val address: String,
    val tag: String,
    @Column(name = "chain")
    @Enumerated(EnumType.STRING)
    val network: Network
)