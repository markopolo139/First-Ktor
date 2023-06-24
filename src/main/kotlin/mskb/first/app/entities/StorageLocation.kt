package mskb.first.app.entities

data class StorageLocation(var name: String, val assignedEquipment: List<Equipment>, var default: Boolean = false)