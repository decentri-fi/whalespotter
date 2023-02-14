package fi.decentri.whalespotter.whale

import jakarta.persistence.*

@Entity
@Table(name = "whales")
class Whale(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,
    var address: String,
    var ens: String,
    var logo: String,
    var importance: Long
)