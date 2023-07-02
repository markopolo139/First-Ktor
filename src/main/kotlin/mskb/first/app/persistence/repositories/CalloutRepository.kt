package mskb.first.app.persistence.repositories

import mskb.first.app.entities.Callout
import mskb.first.app.entities.Section
import mskb.first.app.entities.enums.CalloutType
import mskb.first.app.exceptions.EntityNotFound
import mskb.first.app.persistence.DatabaseFactory.dbQuery
import mskb.first.app.persistence.entities.CalloutEntity
import mskb.first.app.persistence.schema.CalloutTable
import mskb.first.app.persistence.schema.SectionTable
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import java.time.LocalDateTime

class CalloutRepository: CrudRepository<Callout, Int, CalloutEntity> {

    private val sectionRepository = SectionRepository()

    override suspend fun getAll(): List<CalloutEntity> = dbQuery { CalloutEntity.all().toList() }

    override suspend fun getById(id: Int): CalloutEntity = dbQuery {
        CalloutEntity.findById(id) ?: throw EntityNotFound()
    }

    suspend fun filterQuery(
        idStart: Int?, idEnd: Int?, alarmDateStart: LocalDateTime?, alarmDateEnd: LocalDateTime?, type: CalloutType?, location: String?
    ): List<CalloutEntity> = dbQuery {
        val query = Op.build {
            (if (idStart != null) CalloutTable.id greaterEq idStart else CalloutTable.id greater 0) and
            (if (idEnd != null) CalloutTable.id lessEq idEnd else CalloutTable.id greater 0) and
            (if (alarmDateStart != null) CalloutTable.alarmDate greaterEq alarmDateStart else CalloutTable.id greater 0) and
            (if (alarmDateEnd != null) CalloutTable.alarmDate lessEq alarmDateEnd else CalloutTable.id greater 0) and
            (if (type != null) CalloutTable.type eq type else CalloutTable.id greater 0) and
            (if (location != null) CalloutTable.location eq location else CalloutTable.id greater 0)
        }

        CalloutEntity.wrapRows(
            CalloutTable.innerJoin(SectionTable).select(query)
        ).toList()
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