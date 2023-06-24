package mskb.first.app.persistence.entities

import mskb.first.app.persistence.schema.EquipmentTable
import mskb.first.app.persistence.schema.StorageLocationTable
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.EntityID

class StorageLocationEntity(id: EntityID<Int>): IntEntity(id) {
    companion object: EntityClass<Int, StorageLocationEntity>(StorageLocationTable)
    var name by StorageLocationTable.name
    var default by StorageLocationTable.default
    val equipment by EquipmentEntity referrersOn EquipmentTable.storageLocation
}