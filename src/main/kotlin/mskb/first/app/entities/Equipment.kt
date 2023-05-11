package mskb.first.app.entities

data class Equipment(
    val id: Int?, var name: String, var serialNumber: String, var quantity: Int, var category: String,
    var storageLocation: String, val parameters: MutableList<EquipmentParameter>
)