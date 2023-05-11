package mskb.first.app.persistence.schema

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object FireTruckParameterTable: IntIdTable("fire_truck_parameters") {
    val fireTruckId = optReference("fire_truck_id", FireTruckTable, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val key = varchar("key", 256)
    val value = varchar("value", 256)

    init {
        uniqueIndex(fireTruckId, key)
    }
}