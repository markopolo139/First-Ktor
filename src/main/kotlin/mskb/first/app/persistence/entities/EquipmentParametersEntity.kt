package mskb.first.app.persistence.entities

import mskb.first.app.persistence.schema.EquipmentParameterTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class EquipmentParametersEntity(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<EquipmentParametersEntity>(EquipmentParameterTable)
    var key by EquipmentParameterTable.key
    var value by EquipmentParameterTable.value
}