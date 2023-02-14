package fi.decentri.whalespotter.whale

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WhaleService(
    private val whaleRepository: WhaleRepository
) {
    @Transactional(readOnly = true)
    fun getWhales(pageable: Pageable): Page<Whale> {
        return whaleRepository.findAll(pageable)
    }
}