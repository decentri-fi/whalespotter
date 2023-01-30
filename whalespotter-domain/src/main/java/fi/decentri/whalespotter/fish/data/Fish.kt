package fi.decentri.whalespotter.fish.data

import jakarta.persistence.Entity
import jakarta.persistence.Id

@Entity
class Fish(
    @Id
    var id: String,
    var address: String,
    var owner: String
)