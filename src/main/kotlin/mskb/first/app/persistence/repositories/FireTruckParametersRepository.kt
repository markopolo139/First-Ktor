package mskb.first.app.persistence.repositories

import io.ktor.utils.io.*
import mskb.first.app.entities.FireTruckParameter
import mskb.first.app.exceptions.EntityNotFound
import mskb.first.app.exceptions.FeatureNotImplemented
import mskb.first.app.persistence.DatabaseFactory.dbQuery
import mskb.first.app.persistence.entities.EquipmentParametersEntity
import mskb.first.app.persistence.entities.FireTruckEntity
import mskb.first.app.persistence.entities.FireTruckParametersEntity
import mskb.first.app.persistence.schema.EquipmentParameterTable
import mskb.first.app.persistence.schema.FireTruckParameterTable
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and

class FireTruckParametersRepository : CrudRepository<FireTruckParameter, Int, FireTruckParametersEntity> {
    override suspend fun getAll(): List<FireTruckParametersEntity> = dbQuery {
        FireTruckParametersEntity.all().toList()
    }

    override suspend fun getById(id: Int): FireTruckParametersEntity = dbQuery {
        FireTruckParametersEntity.findById(id) ?: throw EntityNotFound()
    }

    override suspend fun save(entity: FireTruckParameter): FireTruckParametersEntity = dbQuery {
        FireTruckParametersEntity.new {
            key = entity.key
            value = entity.value
        }
    }

    suspend fun save(entity: FireTruckParameter, fireTruck: FireTruckEntity): FireTruckParametersEntity = dbQuery {
        FireTruckParametersEntity.new {
            key = entity.key
            value = entity.value
            this.fireTruck = fireTruck
        }
    }

    override suspend fun saveAll(entities: List<FireTruckParameter>): List<FireTruckParametersEntity> = dbQuery {
        entities.map { save(it) }
    }

    suspend fun saveAll(
        entities: List<FireTruckParameter>, fireTruck: FireTruckEntity
    ): List<FireTruckParametersEntity> = dbQuery {
        entities.map { save(it, fireTruck) }
    }

    suspend fun update(parentId: Int, key: String, newValue: String): Boolean = dbQuery {
        FireTruckParametersEntity.find {
            (FireTruckParameterTable.key eq key) and (FireTruckParameterTable.fireTruckId eq parentId)
        }.firstOrNull()?.apply { value = newValue }

        true
    }

    override suspend fun update(entity: FireTruckParameter): Boolean = dbQuery {
        throw FeatureNotImplemented()
    }

    override suspend fun delete(id: Int): Boolean = dbQuery {
        throw FeatureNotImplemented()
    }

    suspend fun delete(parentId: Int, key: String): Boolean = dbQuery {
        FireTruckParametersEntity.find {
            (FireTruckParameterTable.key eq key) and (FireTruckParameterTable.fireTruckId eq parentId)
        }.firstOrNull()?.delete()

        true
    }
}