package mskb.first.app.persistence.schema

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object EquipmentParameterTable: Table("equipment_parameters") {
    val equipmentId = reference("equipment_id", EquipmentTable, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val key = varchar("key", 256)
    val value = varchar("value", 256)

    override val primaryKey: PrimaryKey = PrimaryKey(equipmentId, key, name = "PK_EquipmentParameters")
}