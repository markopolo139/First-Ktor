package mskb.first.app.persistence.schema

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object FireTruckEquipmentTable: Table("fire_truck_equipment") {
    val fireTruck = reference("fire_truck_id", FireTruckTable, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
    val equipment = reference("equipment_id", EquipmentTable, ReferenceOption.CASCADE, ReferenceOption.CASCADE)
}