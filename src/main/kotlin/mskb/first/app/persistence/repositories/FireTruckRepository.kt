package mskb.first.app.persistence.repositories

import mskb.first.app.entities.Equipment
import mskb.first.app.entities.FireTruck
import mskb.first.app.entities.FireTruckParameter
import mskb.first.app.entities.StorageLocation
import mskb.first.app.exceptions.AppException
import mskb.first.app.exceptions.EntityNotFound
import mskb.first.app.exceptions.FeatureNotImplemented
import mskb.first.app.persistence.DatabaseFactory.dbQuery
import mskb.first.app.persistence.entities.EquipmentEntity
import mskb.first.app.persistence.entities.FireTruckEntity
import mskb.first.app.persistence.schema.FireTruckTable
import mskb.first.app.persistence.schema.StorageLocationTable
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.dao.with
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greater
import org.jetbrains.exposed.sql.SqlExpressionBuilder.greaterEq
import org.jetbrains.exposed.sql.SqlExpressionBuilder.lessEq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.statements.api.ExposedBlob
import java.time.LocalDate

class FireTruckRepository: CrudRepository<FireTruck, Int, FireTruckEntity> {

    private val parameterRepository = FireTruckParametersRepository()
    private val storageLocationRepository = StorageLocationRepository()

    override suspend fun getAll(): List<FireTruckEntity> = dbQuery {
        FireTruckEntity.all().filter { !it.archived }.toList()
    }

    suspend fun getAllWithArchived(): List<FireTruckEntity> = dbQuery {
        FireTruckEntity.all().toList()
    }

    override suspend fun getById(id: Int): FireTruckEntity = dbQuery {
        FireTruckEntity.findById(id) ?: throw EntityNotFound()
    }

    suspend fun filterQuery(
        idStart: Int?, idEnd: Int?, name: String?, vin: String?, productionYearStart: Int?, productionYearEnd: Int?,
        licensePlate: String?, operationalNumber: String?, type: String?, totalWeightStart: Int?, totalWeightEnd: Int?,
        horsepowerStart: Int?, horsepowerEnd: Int?, numberOfSeatsStart: Int?, numberOfSeatsEnd: Int?, mileageStart: Int?,
        mileageEnd: Int?, vehicleInspectionExpiryDateStart: LocalDate?, vehicleInspectionExpiryDateEnd: LocalDate?, 
        insuranceExpiryDateStart: LocalDate?, insuranceExpiryDateEnd: LocalDate?
    ): List<FireTruckEntity> = dbQuery {
        val query = Op.build {
            (if (idStart != null) FireTruckTable.id greaterEq idStart else FireTruckTable.id greater 0) and
            (if (idEnd != null) FireTruckTable.id lessEq idEnd else FireTruckTable.id greater 0) and
            (if (name != null) FireTruckTable.name eq name else FireTruckTable.id greater 0) and
            (if (vin != null) FireTruckTable.vin eq vin else FireTruckTable.id greater 0) and
            (if (productionYearStart != null) FireTruckTable.productionYear greaterEq productionYearStart else FireTruckTable.id greater 0) and
            (if (productionYearEnd != null) FireTruckTable.productionYear lessEq productionYearEnd else FireTruckTable.id greater 0) and
            (if (licensePlate != null) FireTruckTable.licensePlate eq licensePlate else FireTruckTable.id greater 0) and
            (if (operationalNumber != null) FireTruckTable.operationalNumber eq operationalNumber else FireTruckTable.id greater 0) and
            (if (type != null) FireTruckTable.type eq type else FireTruckTable.id greater 0) and
            (if (totalWeightStart != null) FireTruckTable.totalWeight greaterEq totalWeightStart else FireTruckTable.id greater 0) and
            (if (totalWeightEnd != null) FireTruckTable.totalWeight lessEq totalWeightEnd else FireTruckTable.id greater 0) and
            (if (horsepowerStart != null) FireTruckTable.horsepower greaterEq horsepowerStart else FireTruckTable.id greater 0) and
            (if (horsepowerEnd != null) FireTruckTable.horsepower lessEq horsepowerEnd else FireTruckTable.id greater 0) and
            (if (numberOfSeatsStart != null) FireTruckTable.numberOfSeats greaterEq numberOfSeatsStart else FireTruckTable.id greater 0) and
            (if (numberOfSeatsEnd != null) FireTruckTable.numberOfSeats lessEq numberOfSeatsEnd else FireTruckTable.id greater 0) and
            (if (mileageStart != null) FireTruckTable.mileage greaterEq mileageStart else FireTruckTable.id greater 0) and
            (if (mileageEnd != null) FireTruckTable.mileage lessEq mileageEnd else FireTruckTable.id greater 0) and
            (if (vehicleInspectionExpiryDateStart != null) FireTruckTable.vehicleInspectionExpiryDate greaterEq vehicleInspectionExpiryDateStart else FireTruckTable.id greater 0) and
            (if (vehicleInspectionExpiryDateEnd != null) FireTruckTable.vehicleInspectionExpiryDate lessEq vehicleInspectionExpiryDateEnd else FireTruckTable.id greater 0) and
            (if (insuranceExpiryDateStart != null) FireTruckTable.insuranceExpiryDate greaterEq insuranceExpiryDateStart else FireTruckTable.id greater 0) and
            (if (insuranceExpiryDateEnd != null) FireTruckTable.insuranceExpiryDate lessEq insuranceExpiryDateEnd else FireTruckTable.id greater 0)
        }

        FireTruckEntity.wrapRows(
            FireTruckTable.innerJoin(StorageLocationTable).select(query)
        ).toList()
    }

    override suspend fun save(entity: FireTruck): FireTruckEntity {
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
                archived = false
            }
        }

        parameterRepository.saveAll(entity.parameters, fireTruck)

        storageLocationRepository.save(
            StorageLocation(null, fireTruck.name + fireTruck.operationalNumber, emptyList(), false)
        )

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

    suspend fun updateParameter(id: Int, parameter: FireTruckParameter): Boolean = dbQuery {
        parameterRepository.update(id, parameter.key, parameter.value)
    }

    suspend fun removeParameter(id: Int, parameterKey: String): FireTruckEntity = dbQuery {
        parameterRepository.delete(id, parameterKey)
        val entity = FireTruckEntity.findById(id)?.load(FireTruckEntity::parameters) ?: throw EntityNotFound()
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

        true
    }

    override suspend fun delete(id: Int): Boolean = throw FeatureNotImplemented()

    suspend fun archive(id:Int): Boolean = dbQuery {
        val fireTruck = FireTruckEntity.findById(id) ?: throw EntityNotFound()
        fireTruck.archived = true

        val storage = storageLocationRepository.findByName(fireTruck.name + fireTruck.operationalNumber)

        storageLocationRepository.delete(storage.id.value)
        true
    }
}