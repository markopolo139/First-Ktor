package mskb.first.app.persistence.entities

import mskb.first.app.persistence.schema.FireTruckEquipmentTable
import mskb.first.app.persistence.schema.FireTruckParameterTable
import mskb.first.app.persistence.schema.FireTruckTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.SizedIterable

class FireTruckEntity(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<FireTruckEntity>(FireTruckTable)
    var name by FireTruckTable.name
    var image by FireTruckTable.image
    var vin by FireTruckTable.vin
    var productionYear by FireTruckTable.productionYear
    var licensePlate by FireTruckTable.licensePlate
    var operationalNumber by FireTruckTable.operationalNumber
    var type by FireTruckTable.type
    var totalWeight by FireTruckTable.totalWeight
    var horsepower by FireTruckTable.horsepower
    var numberOfSeats by FireTruckTable.numberOfSeats
    var mileage by FireTruckTable.mileage
    var vehicleInspectionExpiryDate by FireTruckTable.vehicleInspectionExpiryDate
    var insuranceExpiryDate by FireTruckTable.insuranceExpiryDate
    val equipment: SizedIterable<EquipmentEntity>? by EquipmentEntity via FireTruckEquipmentTable
    val parameters: SizedIterable<FireTruckParametersEntity>?
        by FireTruckParametersEntity optionalReferrersOn FireTruckParameterTable.fireTruckId
}