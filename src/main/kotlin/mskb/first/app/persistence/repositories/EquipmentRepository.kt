package mskb.first.app.persistence.repositories

import mskb.first.app.entities.Equipment
import mskb.first.app.entities.EquipmentParameter
import mskb.first.app.entities.StorageLocation
import mskb.first.app.entities.enums.CalloutType
import mskb.first.app.exceptions.EntityNotFound
import mskb.first.app.exceptions.NoDefaultStorage
import mskb.first.app.persistence.DatabaseFactory.dbQuery
import mskb.first.app.persistence.entities.CalloutEntity
import mskb.first.app.persistence.entities.EquipmentEntity
import mskb.first.app.persistence.entities.FireTruckEntity
import mskb.first.app.persistence.entities.StorageLocationEntity
import mskb.first.app.persistence.schema.EquipmentTable
import mskb.first.app.persistence.schema.SectionTable
import mskb.first.app.persistence.schema.StorageLocationTable
import mskb.first.app.utils.toApp
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greater
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import java.time.LocalDateTime

class EquipmentRepository: CrudRepository<Equipment, Int, EquipmentEntity> {

    private val parameterRepository = EquipmentParametersRepository()
    private val storageLocationRepository = StorageLocationRepository()

    override suspend fun getAll(): List<EquipmentEntity> = dbQuery {
        EquipmentEntity.all().toList()
    }

    override suspend fun getById(id: Int): EquipmentEntity = dbQuery {
        EquipmentEntity.findById(id) ?: throw EntityNotFound()
    }

    suspend fun getByStorageName(storageName: String): List<EquipmentEntity> {
        val storageId = storageLocationRepository.findByName(storageName).id

        return dbQuery {
            EquipmentEntity.find { EquipmentTable.storageLocation eq storageId }.toList()
        }
    }

    suspend fun filterQuery(
        idStart: Int?, idEnd: Int?, name: String?, serialNumber: String?, quantityStart: Int?, quantityEnd: Int?, category: String?, storageLocation: String?,
    ): List<EquipmentEntity> = dbQuery {
        val query = Op.build {
            (if (idStart != null) EquipmentTable.id greaterEq idStart else EquipmentTable.id greater 0) and
            (if (idEnd != null) EquipmentTable.id lessEq idEnd else EquipmentTable.id greater 0) and
            (if (name != null) EquipmentTable.name eq name else EquipmentTable.id greater 0) and
            (if (serialNumber != null) EquipmentTable.serialNumber eq serialNumber else EquipmentTable.id greater 0) and
            (if (quantityStart != null) EquipmentTable.quantity greaterEq quantityStart else EquipmentTable.id greater 0) and
            (if (quantityEnd != null) EquipmentTable.quantity lessEq quantityEnd else EquipmentTable.id greater 0) and
            (if (category != null) EquipmentTable.category eq category else EquipmentTable.id greater 0) and
            (if (storageLocation != null) StorageLocationTable.name eq storageLocation else EquipmentTable.id greater 0)
        }

        EquipmentEntity.wrapRows(
            EquipmentTable.innerJoin(StorageLocationTable).select(query)
        ).toList()
    }

    override suspend fun save(entity: Equipment): EquipmentEntity {
        val storage = if (entity.storageLocation == "default" || entity.storageLocation.isBlank())
            storageLocationRepository.getDefault()
        else storageLocationRepository.findByName(entity.storageLocation)

        val equipment = dbQuery {
            EquipmentEntity.new(entity.id) {
                name = entity.name
                serialNumber = entity.serialNumber
                quantity = entity.quantity
                category = entity.category
                storageLocation = storage
            }
        }

        parameterRepository.saveAll(entity.parameters, equipment)

        return equipment
    }

    override suspend fun saveAll(entities: List<Equipment>): List<EquipmentEntity> = dbQuery { entities.map { save(it) } }

    suspend fun changeLocation(entity: Equipment, newStorageName: String) {
        val storage = storageLocationRepository.findByName(newStorageName)

        dbQuery {
            EquipmentEntity.findById(entity.id ?: -1)?.apply {
                storageLocation = storage
            } ?: throw EntityNotFound()
        }
    }

    suspend fun addParameter(id: Int, parameter: EquipmentParameter): EquipmentEntity = dbQuery {
        val entity = EquipmentEntity.findById(id) ?: throw EntityNotFound()
        parameterRepository.save(parameter, entity)

        entity
    }

    suspend fun updateParameter(id: Int, parameter: EquipmentParameter): Boolean = dbQuery {
        parameterRepository.update(id, parameter.key, parameter.value)
    }

    suspend fun removeParameter(id: Int, parameterKey: String): EquipmentEntity = dbQuery {
        parameterRepository.delete(id, parameterKey)
        val entity = EquipmentEntity.findById(id)?.load(EquipmentEntity::parameters) ?: throw EntityNotFound()
        entity
    }

    override suspend fun update(entity: Equipment): Boolean {

        val storage = storageLocationRepository.findByName(entity.storageLocation)

        dbQuery {
            EquipmentEntity.findById(entity.id ?: -1)?.apply {
                name = entity.name
                serialNumber = entity.serialNumber
                quantity = entity.quantity
                category = entity.category
                storageLocation = storage
            } ?: throw EntityNotFound()
        }

        return true
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        EquipmentEntity.findById(id)?.delete() ?: throw EntityNotFound()
        true
    }
}