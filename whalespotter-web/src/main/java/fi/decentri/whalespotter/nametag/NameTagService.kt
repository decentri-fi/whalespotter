package fi.decentri.whalespotter.nametag

import io.github.reactivecircus.cache4k.Cache
import jakarta.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class NameTagService(
    private val nameTagRepository: NameTagRepository
) {

    val cache = Cache.Builder().build<String, String>()

    val logger = LoggerFactory.getLogger(this::class.java)

    @PostConstruct
    fun init() {
        logger.info("Initializing NameTagService")
        nameTagRepository.findAll().forEach {
            cache.put(
                it.address.lowercase(),
                it.tag
            )
        }
        logger.info("Initialized NameTagService with ${cache.asMap().size} entries")
    }

    suspend fun getByAddress(address: String): NameTagVO? {
        return cache.get(address.lowercase())?.let {
            NameTagVO(address, it)
        }
    }

    fun getAll(): List<NameTagVO> {
        return cache.asMap().map {
            NameTagVO(it.key as String, it.value)
        }
    }
}