package fi.decentri.whalespotter.decentrifi.domain

class GetEventLogsCommand(
        val addresses: List<String>,
        val topic: String,
        val optionalTopics: List<String?> = emptyList()
    )