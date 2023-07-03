package mskb.first.app.services

import mskb.first.app.entities.FireTruck
import mskb.first.app.entities.FireTruckParameter
import mskb.first.app.persistence.repositories.FireTruckRepository
import java.time.LocalDate

class FireTruckService {
    private val fireTruckRepository = FireTruckRepository()

    suspend fun getAll() = fireTruckRepository.getAll()

    suspend fun getAllWithArchived() = fireTruckRepository.getAllWithArchived()

    suspend fun getById(id: Int) = fireTruckRepository.getById(id)

    suspend fun filterQuery(
        idStart: Int?, idEnd: Int?, name: String?, vin: String?, productionYearStart: Int?, productionYearEnd: Int?,
        licensePlate: String?, operationalNumber: String?, type: String?, totalWeightStart: Int?, totalWeightEnd: Int?,
        horsepowerStart: Int?, horsepowerEnd: Int?, numberOfSeatsStart: Int?, numberOfSeatsEnd: Int?, mileageStart: Int?,
        mileageEnd: Int?, vehicleInspectionExpiryDateStart: LocalDate?, vehicleInspectionExpiryDateEnd: LocalDate?,
        insuranceExpiryDateStart: LocalDate?, insuranceExpiryDateEnd: LocalDate?
    ) = fireTruckRepository.filterQuery(
        idStart, idEnd, name, vin, productionYearStart, productionYearEnd, licensePlate, operationalNumber, type,
        totalWeightStart, totalWeightEnd, horsepowerStart, horsepowerEnd, numberOfSeatsStart, numberOfSeatsEnd,
        mileageStart, mileageEnd, vehicleInspectionExpiryDateStart, vehicleInspectionExpiryDateEnd,
        insuranceExpiryDateStart, insuranceExpiryDateEnd
    )

    suspend fun save(fireTruck: FireTruck) = fireTruckRepository.save(fireTruck)

    suspend fun saveAll(fireTrucks: List<FireTruck>) = fireTruckRepository.saveAll(fireTrucks)

    suspend fun addParameter(id: Int, parameter: FireTruckParameter) = fireTruckRepository.addParameter(id, parameter)

    suspend fun updateParameter(id: Int, parameter: FireTruckParameter) = fireTruckRepository.updateParameter(id, parameter)

    suspend fun removeParameter(id: Int, parameterKey: String) = fireTruckRepository.removeParameter(id, parameterKey)

    suspend fun update(fireTruck: FireTruck) = fireTruckRepository.update(fireTruck)

    suspend fun delete(id: Int) = fireTruckRepository.delete(id)

    suspend fun archive(id: Int) = fireTruckRepository.archive(id)

}