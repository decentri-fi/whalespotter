package fi.decentri.client.domain

import java.math.BigInteger

class TransactionVO(
    val hash: String,
    val blockNumber: BigInteger,
    val from: String,
    val to: String?,
    val time: Long,
    val value: BigInteger
)