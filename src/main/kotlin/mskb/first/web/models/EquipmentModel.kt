package mskb.first.web.models

import kotlinx.serialization.Serializable

@Serializable
class EquipmentModel(
    val id: Int?, var name: String, var serialNumber: String, var quantity: Int, var category: String,
    var storageLocation: String, val parameters: MutableList<EquipmentParameterModel>
)