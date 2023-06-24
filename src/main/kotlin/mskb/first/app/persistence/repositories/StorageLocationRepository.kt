package mskb.first.app.persistence.repositories

import mskb.first.app.entities.StorageLocation
import mskb.first.app.persistence.entities.StorageLocationEntity

class StorageLocationRepository: CrudRepository<StorageLocation, Int, StorageLocationEntity> {
    override suspend fun getAll(): List<StorageLocationEntity> {
        TODO("Not yet implemented")
    }

    suspend fun findByName(name: String): StorageLocationEntity {
        TODO("Not yet implemented")
    }

    suspend fun getDefault(): StorageLocationEntity {
        TODO("Not yet implemented")
    }

    override suspend fun getById(id: Int): StorageLocationEntity {
        TODO("Not yet implemented")
    }

    override suspend fun save(entity: StorageLocation): StorageLocationEntity {
        TODO("Not yet implemented")
    }

    override suspend fun saveAll(entities: List<StorageLocation>): List<StorageLocationEntity> {
        TODO("Not yet implemented")
    }

    override suspend fun update(entity: StorageLocation): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun delete(id: Int): Boolean {
        TODO("Not yet implemented")
    }
}