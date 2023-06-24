package mskb.first.app.persistence.schema

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object EquipmentTable: IntIdTable("equipments", "equipment_id") {
    val name = varchar("name", 256)
    val serialNumber = varchar("serial_number", 256)
    val quantity = integer("quantity")
    val category = varchar("category", 256)
    val storageLocation = reference(
        "storage_location_id",
        StorageLocationTable,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
}