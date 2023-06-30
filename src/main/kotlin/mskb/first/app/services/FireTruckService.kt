package mskb.first.app.services

import mskb.first.app.entities.FireTruck
import mskb.first.app.entities.FireTruckParameter
import mskb.first.app.persistence.repositories.FireTruckRepository

class FireTruckService {
    private val fireTruckRepository = FireTruckRepository()

    suspend fun getAll() = fireTruckRepository.getAll()

    suspend fun getAllWithArchived() = fireTruckRepository.getAllWithArchived()

    suspend fun getById(id: Int) = fireTruckRepository.getById(id)

    suspend fun save(fireTruck: FireTruck) = fireTruckRepository.save(fireTruck)

    suspend fun saveAll(fireTrucks: List<FireTruck>) = fireTruckRepository.saveAll(fireTrucks)

    suspend fun addParameter(id: Int, parameter: FireTruckParameter) = fireTruckRepository.addParameter(id, parameter)

    suspend fun updateParameter(id: Int, parameter: FireTruckParameter) = fireTruckRepository.updateParameter(id, parameter)

    suspend fun removeParameter(id: Int, parameterKey: String) = fireTruckRepository.removeParameter(id, parameterKey)

    suspend fun update(fireTruck: FireTruck) = fireTruckRepository.update(fireTruck)

    suspend fun delete(id: Int) = fireTruckRepository.delete(id)

    suspend fun archive(id: Int) = fireTruckRepository.archive(id)

}