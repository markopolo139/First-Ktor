package mskb.first.app.persistence.schema

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object FireTruckParameterTable: Table("fire_truck_parameters") {
    val fireTruckId = reference("fire_truck_id", FireTruckTable, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val key = varchar("key", 256)
    val value = varchar("value", 256)
}