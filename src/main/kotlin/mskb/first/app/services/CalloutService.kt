package mskb.first.app.services

import mskb.first.app.entities.Callout
import mskb.first.app.entities.Section
import mskb.first.app.entities.enums.CalloutType
import mskb.first.app.persistence.repositories.CalloutRepository
import java.time.LocalDateTime

class CalloutService {
    private val calloutRepository = CalloutRepository()

    suspend fun getAll() = calloutRepository.getAll()

    suspend fun getById(id: Int) = calloutRepository.getById(id)

    suspend fun filterQuery(
        idStart: Int?, idEnd: Int?, alarmDateStart: LocalDateTime?, alarmDateEnd: LocalDateTime?,
        type: CalloutType?, location: String?
    ) = calloutRepository.filterQuery(idStart, idEnd, alarmDateStart, alarmDateEnd, type, location)

    suspend fun save(callout: Callout) = calloutRepository.save(callout)

    suspend fun saveAll(callouts: List<Callout>) = calloutRepository.saveAll(callouts)

    suspend fun addSection(id: Int, section: Section) = calloutRepository.addSection(id, section)

    suspend fun updateSection(section: Section) = calloutRepository.updateSection(section)

    suspend fun removeSection(id: Int, section: Section) = calloutRepository.removeSection(id, section)

    suspend fun update(callout: Callout) = calloutRepository.update(callout)

    suspend fun delete(id: Int) = calloutRepository.delete(id)
}