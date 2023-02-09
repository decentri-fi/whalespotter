package fi.decentri.whalespotter.event

import jakarta.persistence.*
import fi.decentri.whalespotter.transaction.data.Transaction
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.annotations.Type
import org.hibernate.type.SqlTypes
import java.sql.SQLType

@Entity
@Table(name = "defi_events")
class DefiEvent(
    @Id
    val id: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    val type: DefiEventType,
    @ManyToOne
    @JoinColumn(name = "transaction_id")
    val transaction: Transaction,
    val protocol: String? = null,
    @JdbcTypeCode(SqlTypes.JSON)
    val metadata: Map<String, Any>
)
