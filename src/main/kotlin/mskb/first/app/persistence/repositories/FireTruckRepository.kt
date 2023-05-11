package mskb.first.app.persistence.repositories

import mskb.first.app.entities.Equipment
import mskb.first.app.entities.FireTruck
import mskb.first.app.entities.FireTruckParameter
import mskb.first.app.exceptions.AppException
import mskb.first.app.exceptions.EntityNotFound
import mskb.first.app.persistence.DatabaseFactory.dbQuery
import mskb.first.app.persistence.entities.EquipmentEntity
import mskb.first.app.persistence.entities.FireTruckEntity
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.dao.with
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.statements.api.ExposedBlob

class FireTruckRepository: CrudRepository<FireTruck, Int, FireTruckEntity> {

    private val parameterRepository = FireTruckParametersRepository()
    private val equipmentRepository = EquipmentRepository()

    override suspend fun getAll(): List<FireTruckEntity> = dbQuery {
        FireTruckEntity.all().toList()
    }

    override suspend fun getById(id: Int): FireTruckEntity = dbQuery {
        FireTruckEntity.findById(id) ?: throw EntityNotFound()
    }

    override suspend fun save(entity: FireTruck): FireTruckEntity {
        val equipments = equipmentRepository.saveAll(entity.equipment)

        val fireTruck = dbQuery {
            FireTruckEntity.new(entity.id) {
                name = entity.name
                image = ExposedBlob(entity.image)
                vin = entity.vin
                productionYear = entity.productionYear
                licensePlate = entity.licensePlate
                operationalNumber = entity.operationNumber
                type = entity.type
                totalWeight = entity.totalWeight
                horsepower = entity.horsepower
                numberOfSeats = entity.numberOfSeats
                mileage = entity.mileage
                vehicleInspectionExpiryDate = entity.vehicleInspectionExpiryDate
                insuranceExpiryDate = entity.insuranceExpiryDate
            }
        }

        dbQuery {
            fireTruck.equipment = SizedCollection(equipments)
        }

        parameterRepository.saveAll(entity.parameters, fireTruck)

        return fireTruck
    }

    override suspend fun saveAll(entities: List<FireTruck>): List<FireTruckEntity> = dbQuery {
        entities.map { save(it) }
    }

    suspend fun addParameter(id: Int, parameter: FireTruckParameter): FireTruckEntity = dbQuery {
        val entity = FireTruckEntity.findById(id) ?: throw EntityNotFound()
        parameterRepository.save(parameter, entity)

        entity
    }

    suspend fun removeParameter(id: Int, parameterKey: String): FireTruckEntity = dbQuery {
        parameterRepository.delete(id, parameterKey)
        val entity = FireTruckEntity.findById(id)?.load(FireTruckEntity::parameters) ?: throw EntityNotFound()
        entity
    }

    suspend fun addEquipment(id: Int, equipment: Equipment): FireTruckEntity = dbQuery {
        val entity = FireTruckEntity.findById(id)?.load(FireTruckEntity::equipment) ?: throw EntityNotFound()

        val newEquipment = entity.equipment.toMutableList()
        newEquipment.add(equipmentRepository.save(equipment))

        entity.equipment = SizedCollection(newEquipment)

        entity
    }

    suspend fun removeEquipment(id: Int, equipmentId: Int): FireTruckEntity = dbQuery {
        val entity = FireTruckEntity.findById(id)?.load(FireTruckEntity::equipment) ?: throw EntityNotFound()

        val remove = equipmentRepository.getById(equipmentId)
        val newEquipment = entity.equipment.toMutableList()
        newEquipment.remove(remove)

        entity.equipment = SizedCollection(newEquipment)

        entity
    }

    override suspend fun update(entity: FireTruck): Boolean = dbQuery {
        FireTruckEntity.findById(entity.id ?: -1)?.apply {
            name = entity.name
            image = ExposedBlob(entity.image)
            vin = entity.vin
            productionYear = entity.productionYear
            licensePlate = entity.licensePlate
            operationalNumber = entity.operationNumber
            type = entity.type
            totalWeight = entity.totalWeight
            horsepower = entity.horsepower
            numberOfSeats = entity.numberOfSeats
            mileage = entity.mileage
            vehicleInspectionExpiryDate = entity.vehicleInspectionExpiryDate
            insuranceExpiryDate = entity.insuranceExpiryDate
        } ?: throw EntityNotFound()

        entity.equipment.forEach {
            try {
                equipmentRepository.update(it)
            } catch (_: AppException) {  }
        }

        true
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        FireTruckEntity.findById(id)?.delete() ?: throw EntityNotFound()
        true
    }
}