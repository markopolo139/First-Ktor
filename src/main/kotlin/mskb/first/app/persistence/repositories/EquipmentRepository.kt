package mskb.first.app.persistence.repositories

import mskb.first.app.entities.Equipment
import mskb.first.app.entities.EquipmentParameter
import mskb.first.app.exceptions.EntityNotFound
import mskb.first.app.persistence.DatabaseFactory.dbQuery
import mskb.first.app.persistence.entities.EquipmentEntity
import mskb.first.app.persistence.entities.FireTruckEntity
import org.jetbrains.exposed.dao.load

class EquipmentRepository: CrudRepository<Equipment, Int, EquipmentEntity> {

    private val parameterRepository = EquipmentParametersRepository()

    override suspend fun getAll(): List<EquipmentEntity> = dbQuery {
        EquipmentEntity.all().toList()
    }

    override suspend fun getById(id: Int): EquipmentEntity = dbQuery {
        EquipmentEntity.findById(id) ?: throw EntityNotFound()
    }

    override suspend fun save(entity: Equipment): EquipmentEntity {
        val equipment = dbQuery {
            EquipmentEntity.new(entity.id) {
                name = entity.name
                serialNumber = entity.serialNumber
                quantity = entity.quantity
                category = entity.category
                storageLocation = entity.storageLocation
            }
        }

        parameterRepository.saveAll(entity.parameters, equipment)

        return equipment
    }

    override suspend fun saveAll(entities: List<Equipment>): List<EquipmentEntity> = dbQuery { entities.map { save(it) } }

    suspend fun addParameter(id: Int, parameter: EquipmentParameter): EquipmentEntity = dbQuery {
        val entity = EquipmentEntity.findById(id) ?: throw EntityNotFound()
        parameterRepository.save(parameter, entity)

        entity
    }

    suspend fun removeParameter(id: Int, parameterKey: String): EquipmentEntity = dbQuery {
        parameterRepository.delete(id, parameterKey)
        val entity = EquipmentEntity.findById(id)?.load(EquipmentEntity::parameters) ?: throw EntityNotFound()
        entity
    }

    override suspend fun update(entity: Equipment): Boolean = dbQuery {
        EquipmentEntity.findById(entity.id ?: -1)?.apply {
            name = entity.name
            serialNumber = entity.serialNumber
            quantity = entity.quantity
            category = entity.category
            storageLocation = entity.storageLocation
        } ?: throw EntityNotFound()

        true
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        EquipmentEntity.findById(id)?.delete() ?: throw EntityNotFound()
        true
    }
}