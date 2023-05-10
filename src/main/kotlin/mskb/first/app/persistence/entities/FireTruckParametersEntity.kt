package mskb.first.app.persistence.entities

import mskb.first.app.persistence.schema.FireTruckParameterTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class FireTruckParametersEntity(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<FireTruckParametersEntity>(FireTruckParameterTable)
    var key by FireTruckParameterTable.key
    var value by FireTruckParameterTable.value
}