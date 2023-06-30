package mskb.first.app.persistence.repositories

import mskb.first.app.entities.StorageLocation
import mskb.first.app.exceptions.DefaultStorageDeleteException
import mskb.first.app.exceptions.EntityNotFound
import mskb.first.app.exceptions.FeatureNotImplemented
import mskb.first.app.exceptions.NoDefaultStorage
import mskb.first.app.persistence.entities.StorageLocationEntity
import mskb.first.app.persistence.DatabaseFactory.dbQuery
import mskb.first.app.persistence.schema.StorageLocationTable

class StorageLocationRepository: CrudRepository<StorageLocation, Int, StorageLocationEntity> {
    private val equipmentRepository = EquipmentRepository()

    override suspend fun getAll(): List<StorageLocationEntity> = dbQuery {
        StorageLocationEntity.all().toList()
    }

    suspend fun findByName(name: String): StorageLocationEntity = dbQuery {
        StorageLocationEntity.find { StorageLocationTable.name eq name }.firstOrNull() ?: throw EntityNotFound()
    }

    suspend fun getDefault(): StorageLocationEntity? = dbQuery {
        StorageLocationEntity.find { StorageLocationTable.default eq true }.firstOrNull()
    }

    override suspend fun getById(id: Int): StorageLocationEntity = dbQuery {
        StorageLocationEntity.findById(id) ?: throw EntityNotFound()
    }

    suspend fun changeDefault(entity: StorageLocation) = dbQuery {
        val newDefault = StorageLocationEntity.findById(entity.id ?: -1) ?: throw EntityNotFound()
        getDefault()?.default = false
        newDefault.default = true
    }

    override suspend fun save(entity: StorageLocation): StorageLocationEntity {
        val storage = dbQuery {
            StorageLocationEntity.new(entity.id) {
                name = entity.name
                default = false
            }
        }

        entity.assignedEquipment.forEach { equipmentRepository.changeLocation(it, storage.name) }

        return storage
    }

    override suspend fun saveAll(entities: List<StorageLocation>): List<StorageLocationEntity> = dbQuery {
        entities.map { save(it) }
    }

    override suspend fun update(entity: StorageLocation): Boolean = throw FeatureNotImplemented()

    override suspend fun delete(id: Int): Boolean = dbQuery {
        val storage = getById(id)
        if (storage.default) throw DefaultStorageDeleteException()
        storage.equipment.forEach { it.storageLocation = getDefault() ?: throw NoDefaultStorage() }
        storage.delete()
        true
    }
}