package fi.decentri.event

import fi.decentri.whalespotter.event.DefiEventType

class DefiEventDTO (
    val type: DefiEventType,
    val protocol: String? = null,
    val metadata: Map<String, Any>
)
