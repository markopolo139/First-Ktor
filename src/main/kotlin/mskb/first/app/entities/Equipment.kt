package mskb.first.app.entities

data class Equipment(
    val id: Int, val name: String, val serialNumber: String, val quantity: Int, val category: String,
    val storageLocation: String, val parameters: MutableList<EquipmentParameter>
)