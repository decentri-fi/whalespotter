package fi.decentri.client.domain

import java.math.BigDecimal

class Claimable(
    val id: String,
    val name: String,
    val dollarValue: BigDecimal
)