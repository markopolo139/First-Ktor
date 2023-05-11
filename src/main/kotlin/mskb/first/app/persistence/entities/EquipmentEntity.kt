package mskb.first.app.persistence.entities

import mskb.first.app.persistence.schema.EquipmentParameterTable
import mskb.first.app.persistence.schema.EquipmentTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class EquipmentEntity(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<EquipmentEntity>(EquipmentTable)
    var name by EquipmentTable.name
    var serialNumber by EquipmentTable.serialNumber
    var quantity by EquipmentTable.quantity
    var category by EquipmentTable.category
    var storageLocation by EquipmentTable.storageLocation
    val parameters by EquipmentParametersEntity optionalReferrersOn EquipmentParameterTable.equipmentId
}