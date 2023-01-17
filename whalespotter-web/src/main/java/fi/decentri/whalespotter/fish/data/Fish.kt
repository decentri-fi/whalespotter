package fi.decentri.whalespotter.fish.data

import jakarta.persistence.Entity

@Entity
class Fish(
    var id: String,
    var address: String,
    var network: String,
    var owner: String
)