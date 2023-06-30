package mskb.first.app.persistence.repositories

import mskb.first.app.entities.Callout
import mskb.first.app.entities.Section
import mskb.first.app.exceptions.EntityNotFound
import mskb.first.app.persistence.DatabaseFactory.dbQuery
import mskb.first.app.persistence.entities.CalloutEntity
import org.jetbrains.exposed.dao.load

class CalloutRepository: CrudRepository<Callout, Int, CalloutEntity> {

    private val sectionRepository = SectionRepository()

    override suspend fun getAll(): List<CalloutEntity> = dbQuery { CalloutEntity.all().toList() }

    override suspend fun getById(id: Int): CalloutEntity = dbQuery {
        CalloutEntity.findById(id) ?: throw EntityNotFound()
    }

    override suspend fun save(entity: Callout): CalloutEntity {
        val callout = dbQuery {
            CalloutEntity.new {
                alarmDate = entity.alarmDate
                type = entity.type
                location = entity.location
                details = entity.details
            }
        }

        sectionRepository.saveAll(entity.sections, callout)

        return callout
    }

    override suspend fun saveAll(entities: List<Callout>): List<CalloutEntity> = dbQuery { entities.map { save(it) } }

    suspend fun addSection(id: Int, section: Section): CalloutEntity = dbQuery {
        val callout = CalloutEntity.findById(id) ?: throw EntityNotFound()
        sectionRepository.save(section, callout)
        callout
    }

    suspend fun updateSection(section: Section): Boolean = dbQuery {
        sectionRepository.update(section)
    }

    suspend fun removeSection(id: Int, section: Section): CalloutEntity = dbQuery {
        sectionRepository.delete(section.id ?: throw EntityNotFound())
        val callout = CalloutEntity.findById(id)?.load(CalloutEntity::sections) ?: throw EntityNotFound()
        callout
    }

    override suspend fun update(entity: Callout): Boolean = dbQuery {
        CalloutEntity.findById(entity.id ?: -1)?.apply {
            alarmDate = entity.alarmDate
            type = entity.type
            location = entity.location
            details = entity.details
        } ?: throw EntityNotFound()
        true
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        CalloutEntity.findById(id)?.delete() ?: throw EntityNotFound()
        true
    }
}