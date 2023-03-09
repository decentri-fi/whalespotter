package fi.decentri.whalespotter.decentrifi.domain

enum class Network(val slug: String, val logo: String) {
    ETHEREUM("ethereum", "ethereum.png"),
    OPTIMISM("optimism", "optimism.png"),
    ARBITRUM("arbitrum", "arbitrum.png"),
    FANTOM("fantom", "fantom.png"),
    AVALANCHE("avalanche", "avalanche.png"),
    BINANCE("binance", "bsc.svg"),
    POLYGON("polygon", "polygon.png"),
    STARKET("starknet.png", "starknet")
}