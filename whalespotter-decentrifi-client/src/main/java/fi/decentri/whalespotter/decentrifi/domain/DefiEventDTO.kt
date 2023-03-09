package fi.decentri.whalespotter.decentrifi.domain


class DefiEventDTO (
    val type: DefiEventType,
    val protocol: String? = null,
    val metadata: Map<String, Any>
)
