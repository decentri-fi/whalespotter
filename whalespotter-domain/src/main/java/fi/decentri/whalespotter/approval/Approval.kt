package fi.decentri.whalespotter.approval

import fi.decentri.whalespotter.network.Network
import jakarta.persistence.*
import java.math.BigInteger

@Entity
@Table(name = "approvals")
class Approval(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val owner: String,
    val spender: String,
    val amount: BigInteger,
    val token: String,
    @Enumerated(value = EnumType.STRING)
    val network: Network
)