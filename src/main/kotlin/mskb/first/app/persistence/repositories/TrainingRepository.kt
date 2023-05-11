package mskb.first.app.persistence.repositories

import mskb.first.app.entities.Training
import mskb.first.app.exceptions.EntityNotFound
import mskb.first.app.persistence.DatabaseFactory.dbQuery
import mskb.first.app.persistence.entities.MemberEntity
import mskb.first.app.persistence.entities.TrainingEntity

class TrainingRepository: CrudRepository<Training, String, TrainingEntity> {
    override suspend fun getAll(): List<TrainingEntity> = dbQuery { TrainingEntity.all().toList() }

    override suspend fun getById(id: String): TrainingEntity = dbQuery {
        TrainingEntity.findById(id) ?: throw EntityNotFound()
    }

    override suspend fun save(entity: Training): TrainingEntity = dbQuery {
        TrainingEntity.new(entity.id) {
            type = entity.type
            trainingDate = entity.trainingDate
            expirationDate = entity.expirationDate

        }
    }

    override suspend fun saveAll(entities: List<Training>): List<TrainingEntity> = dbQuery {
        entities.map { save(it) }
    }

    suspend fun save(entity: Training, member: MemberEntity): TrainingEntity = dbQuery {
        TrainingEntity.new(entity.id) {
            type = entity.type
            trainingDate = entity.trainingDate
            expirationDate = entity.expirationDate
            this.member = member
        }
    }

    suspend fun saveAll(entities: List<Training>, member: MemberEntity): List<TrainingEntity> = dbQuery {
        entities.map { save(it, member) }
    }

    override suspend fun update(entity: Training): Boolean = dbQuery {
        val update = TrainingEntity.findById(entity.id ?: "") ?: throw EntityNotFound()
        update.type = entity.type
        update.trainingDate = entity.trainingDate
        update.expirationDate = entity.expirationDate

        true
    }

    override suspend fun delete(id: String): Boolean = dbQuery {
        TrainingEntity.findById(id)?.delete()
        true
    }
}