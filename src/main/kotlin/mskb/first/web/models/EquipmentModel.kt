package mskb.first.web.models

import kotlinx.serialization.Serializable

@Serializable
class EquipmentModel(
    val id: Int?, val name: String, val serialNumber: String, val quantity: Int, val category: String,
    val storageLocation: String, val parameters: List<EquipmentParameterModel>
)