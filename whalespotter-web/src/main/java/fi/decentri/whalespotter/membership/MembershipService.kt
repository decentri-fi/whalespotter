package fi.decentri.whalespotter.membership

import fi.decentri.whalespotter.decentrifi.DecentrifiClient
import fi.decentri.whalespotter.decentrifi.domain.Network
import org.springframework.stereotype.Component

private const val OPENSEA_COLLECTION = "0x2953399124f0cbb46d2cbacd8a89cf0599974963"
private const val EARLY_BIRD_TOKEN = "59544766117376618043978085178500018507573765124387247653177172736636843196516"

@Component
class MembershipService(private val decentrifiClient: DecentrifiClient) {

    suspend fun isMember(address: String): Boolean {
        return decentrifiClient.getERC5511Balance(
            OPENSEA_COLLECTION,
            address,
            EARLY_BIRD_TOKEN,
            network = Network.POLYGON
        ).balance > 0
    }
}