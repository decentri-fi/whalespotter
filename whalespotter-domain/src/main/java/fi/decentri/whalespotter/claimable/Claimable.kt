package fi.decentri.whalespotter.claimable

import fi.decentri.whalespotter.fish.data.Fish
import jakarta.persistence.*
import java.math.BigDecimal

@Entity
@Table(name = "claimables")
class Claimable(
    @Id
    val id: String,
    @OneToOne
    @JoinColumn(name = "fish_id")
    val fish: Fish,
    @Column(name = "total_claimable")
    val totalClaimable: BigDecimal,
    @Column(name = "notified_user")
    val notifiedUser: Boolean
)