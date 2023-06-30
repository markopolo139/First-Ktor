package mskb.first.app.persistence.repositories

import mskb.first.app.entities.Section
import mskb.first.app.exceptions.EntityNotFound
import mskb.first.app.exceptions.FeatureNotImplemented
import mskb.first.app.persistence.DatabaseFactory.dbQuery
import mskb.first.app.persistence.entities.CalloutEntity
import mskb.first.app.persistence.entities.SectionEntity
import org.jetbrains.exposed.sql.SizedCollection

class SectionRepository: CrudRepository<Section, Int, SectionEntity> {
    private val memberRepository = MemberRepository()
    private val fireTruckRepository = FireTruckRepository()

    override suspend fun getAll(): List<SectionEntity> = dbQuery {
        SectionEntity.all().toList()
    }

    override suspend fun getById(id: Int): SectionEntity = dbQuery {
        SectionEntity.findById(id) ?: throw EntityNotFound()
    }

    override suspend fun save(entity: Section): SectionEntity  {
        val fireTruck = fireTruckRepository.getById(entity.fireTruck?.id ?: -1)
        val crew = entity.crew.map { memberRepository.getById(it.id ?: -1) }

        val section = dbQuery {
            SectionEntity.new {
                departureTime = entity.departureDate
                returnTime = entity.returnDate
                this.fireTruck = fireTruck
            }
        }

        dbQuery {
            section.crew = SizedCollection(crew)
        }

        return section
    }

    suspend fun save(entity: Section, callout: CalloutEntity): SectionEntity {
        val fireTruck = fireTruckRepository.getById(entity.fireTruck?.id ?: -1)
        val crew = entity.crew.map { memberRepository.getById(it.id ?: -1) }

        val section = dbQuery {
            SectionEntity.new {
                departureTime = entity.departureDate
                returnTime = entity.returnDate
                this.fireTruck = fireTruck
                this.callout = callout
            }
        }

        dbQuery {
            section.crew = SizedCollection(crew)
        }

        return section
    }

    override suspend fun saveAll(entities: List<Section>): List<SectionEntity> = dbQuery { entities.map { save(it) } }

    suspend fun saveAll(entities: List<Section>, callout: CalloutEntity): List<SectionEntity> = dbQuery {
        entities.map { save(it, callout) }
    }

    override suspend fun update(entity: Section): Boolean = dbQuery {
        val section = SectionEntity.findById(entity.id ?: -1)?.apply {
            departureTime = entity.departureDate
            returnTime = entity.returnDate
        } ?: throw EntityNotFound()

        val newCrew = section.crew.toMutableSet()
        newCrew.addAll(
            entity.crew.map { memberRepository.getById(it.id ?: -1) }
        )

        section.crew = SizedCollection(newCrew)

        true
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        SectionEntity.findById(id)?.delete() ?: throw EntityNotFound()
        true
    }
}