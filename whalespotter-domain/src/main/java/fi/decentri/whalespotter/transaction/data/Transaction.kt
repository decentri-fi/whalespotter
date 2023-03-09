package fi.decentri.whalespotter.transaction.data

import fi.decentri.whalespotter.decentrifi.domain.Network
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "transactions")
class Transaction(
    @Id
    val id: String,
    @Enumerated(EnumType.STRING)
    val network: Network,
    @Column(name = "from_address")
    val from: String,
    @Column(name = "to_address")
    val to: String?,
    val block: String,
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "block_time")
    val time: Date,
    val value: String
)