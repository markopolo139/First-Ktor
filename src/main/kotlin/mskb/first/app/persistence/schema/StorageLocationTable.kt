package mskb.first.app.persistence.schema

import org.jetbrains.exposed.dao.id.IntIdTable

object StorageLocationTable: IntIdTable("storage_location", "storage_location_id") {
    val name = varchar("name", 256).uniqueIndex()
    val default = bool("default")
}