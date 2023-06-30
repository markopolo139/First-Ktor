package mskb.first.app.services

import mskb.first.app.entities.StorageLocation
import mskb.first.app.persistence.repositories.StorageLocationRepository

class StorageLocationService {
    private val storageLocationRepository = StorageLocationRepository()

    suspend fun getAll() = storageLocationRepository.getAll()

    suspend fun findByName(name: String) = storageLocationRepository.findByName(name)

    suspend fun getDefault() = storageLocationRepository.getDefault()

    suspend fun getById(id: Int) = storageLocationRepository.getById(id)

    suspend fun changeDefault(storageLocation: StorageLocation) = storageLocationRepository.changeDefault(storageLocation)

    suspend fun save(storageLocation: StorageLocation) = storageLocationRepository.save(storageLocation)

    suspend fun saveAll(storageLocations: List<StorageLocation>) = storageLocationRepository.saveAll(storageLocations)

    suspend fun update(storageLocation: StorageLocation) = storageLocationRepository.update(storageLocation)

    suspend fun delete(id: Int) = storageLocationRepository.delete(id)
}