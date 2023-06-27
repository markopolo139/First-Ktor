package mskb.first.app.persistence.schema

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

object FireTruckTable: IntIdTable("fire_trucks", "fire_truck_id") {
    val name = varchar("name", 256)
    val image = blob("image").nullable()
    val vin = varchar("vin", 256)
    val productionYear = integer("production_year")
    val licensePlate = varchar("license_plate", 256)
    val operationalNumber = varchar("operational_number", 256).uniqueIndex()
    val type = varchar("type", 256)
    val totalWeight = integer("total_weight")
    val horsepower = integer("horsepower")
    val numberOfSeats = integer("seats")
    val mileage = integer("mileage")
    val vehicleInspectionExpiryDate = date("vehicle_inspection_expiry_date")
    val insuranceExpiryDate = date("insurance_expiry_date")
    val archived = bool("archived")
}