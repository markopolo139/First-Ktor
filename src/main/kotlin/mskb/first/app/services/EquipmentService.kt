package mskb.first.app.services

import mskb.first.app.entities.Equipment
import mskb.first.app.entities.EquipmentParameter
import mskb.first.app.entities.StorageLocation
import mskb.first.app.persistence.repositories.EquipmentRepository

class EquipmentService {
    private val equipmentRepository = EquipmentRepository()

    suspend fun getAll() = equipmentRepository.getAll()

    suspend fun getById(id: Int) = equipmentRepository.getById(id)

    suspend fun getByStorageName(storageName: String) = equipmentRepository.getByStorageName(storageName)

    suspend fun save(equipment: Equipment) = equipmentRepository.save(equipment)

    suspend fun saveAll(equipments: List<Equipment>) = equipmentRepository.saveAll(equipments)

    suspend fun changeLocation(equipment: Equipment, storageLocationName: String) =
        equipmentRepository.changeLocation(equipment, storageLocationName)

    suspend fun addParameter(id: Int, parameter: EquipmentParameter) = equipmentRepository.addParameter(id, parameter)

    suspend fun updateParameter(id: Int, parameter: EquipmentParameter) = equipmentRepository.updateParameter(id, parameter)

    suspend fun removeParameter(id: Int, parameterKey: String) = equipmentRepository.removeParameter(id, parameterKey)

    suspend fun update(equipment: Equipment) = equipmentRepository.update(equipment)

    suspend fun delete(id: Int) = equipmentRepository.delete(id)
}