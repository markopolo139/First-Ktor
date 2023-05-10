package mskb.first.app.persistence.schema

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object EquipmentParameterTable: IntIdTable("equipment_parameters") {
    val equipmentId = reference("equipment_id", EquipmentTable, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val key = varchar("key", 256)
    val value = varchar("value", 256)

    init {
        uniqueIndex(equipmentId, key)
    }
}