package fi.decentri.whalespotter.decentrifi.domain

import java.math.BigDecimal

class Claimable(
    val id: String,
    val name: String,
    val dollarValue: BigDecimal
)