package mskb.first.app.entities

import java.time.LocalDate

data class FireTruck(
    val id: Int, val name: String, val image: ByteArray, val vin: String, val productionYear: Int, val licensePlate: String,
    val operationNumber: String, val type: String, val totalWeight: Int, val horsepower: Int, val numberOfSeats: Int,
    val mileage: Int, val vehicleInspectionExpiryDate: LocalDate, val insuranceExpiryDate: LocalDate,
    val parameters: MutableList<FireTruckParameter>, val equipment: MutableList<Equipment>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FireTruck

        if (name != other.name) return false
        if (!image.contentEquals(other.image)) return false
        if (vin != other.vin) return false
        if (productionYear != other.productionYear) return false
        if (licensePlate != other.licensePlate) return false
        if (operationNumber != other.operationNumber) return false
        if (type != other.type) return false
        if (totalWeight != other.totalWeight) return false
        if (horsepower != other.horsepower) return false
        if (numberOfSeats != other.numberOfSeats) return false
        if (mileage != other.mileage) return false
        if (vehicleInspectionExpiryDate != other.vehicleInspectionExpiryDate) return false
        if (insuranceExpiryDate != other.insuranceExpiryDate) return false
        if (parameters != other.parameters) return false
        return equipment == other.equipment
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + image.contentHashCode()
        result = 31 * result + vin.hashCode()
        result = 31 * result + productionYear
        result = 31 * result + licensePlate.hashCode()
        result = 31 * result + operationNumber.hashCode()
        result = 31 * result + type.hashCode()
        result = 31 * result + totalWeight
        result = 31 * result + horsepower
        result = 31 * result + numberOfSeats
        result = 31 * result + mileage
        result = 31 * result + vehicleInspectionExpiryDate.hashCode()
        result = 31 * result + insuranceExpiryDate.hashCode()
        result = 31 * result + parameters.hashCode()
        result = 31 * result + equipment.hashCode()
        return result
    }
}