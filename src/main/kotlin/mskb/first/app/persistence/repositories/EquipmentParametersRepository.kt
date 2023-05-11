package mskb.first.app.persistence.repositories

import mskb.first.app.entities.EquipmentParameter
import mskb.first.app.exceptions.FeatureNotImplemented
import mskb.first.app.persistence.DatabaseFactory.dbQuery
import mskb.first.app.persistence.entities.EquipmentEntity
import mskb.first.app.persistence.entities.EquipmentParametersEntity

class EquipmentParametersRepository: CrudRepository<EquipmentParameter, Int, EquipmentParametersEntity> {

    override suspend fun getAll(): List<EquipmentParametersEntity> = dbQuery {
        EquipmentParametersEntity.all().toList()
    }

    override suspend fun getById(id: Int): EquipmentParametersEntity? = dbQuery { EquipmentParametersEntity.findById(id) }

    override suspend fun save(entity: EquipmentParameter): EquipmentParametersEntity = dbQuery {
        EquipmentParametersEntity.new {
            key = entity.key
            value = entity.value
        }
    }

    suspend fun save(entity: EquipmentParameter, equipment: EquipmentEntity): EquipmentParametersEntity = dbQuery {
        EquipmentParametersEntity.new {
            key = entity.key
            value = entity.value
            this.equipment = equipment
        }
    }

    override suspend fun saveAll(entities: List<EquipmentParameter>): List<EquipmentParametersEntity> = dbQuery {
        entities.map { save(it) }.toList()
    }

    suspend fun saveAll(entities: List<EquipmentParameter>, equipment: EquipmentEntity): List<EquipmentParametersEntity> = dbQuery {
            entities.map { save(it, equipment) }
    }

    override suspend fun update(entity: EquipmentParameter): Boolean {
        throw FeatureNotImplemented()
    }

    override suspend fun delete(id: Int): Boolean {
        throw FeatureNotImplemented()
    }
}