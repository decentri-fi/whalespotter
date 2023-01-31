package fi.decentri.whalespotter.account

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "accounts")
class Account(
    @Id
    val id: String? = null
)