package mskb.first.app.persistence.schema

import org.jetbrains.exposed.dao.id.IntIdTable

object EquipmentTable: IntIdTable("equipments", "equipment_id") {
    val name = varchar("name", 256)
    val serialNumber = varchar("serial_number", 256)
    val quantity = integer("quantity")
    val category = varchar("category", 256)
    val storageLocation = varchar("storage_location", 256)
}