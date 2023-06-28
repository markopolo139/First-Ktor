package mskb.first.web.models

import kotlinx.serialization.Serializable

@Serializable
class StorageLocationModel(
    val id: Int?, val name: String, val assignedEquipment: List<EquipmentModel>, val default: Boolean
)