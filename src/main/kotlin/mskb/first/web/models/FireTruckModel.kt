package mskb.first.web.models

import kotlinx.datetime.serializers.LocalDateIso8601Serializer
import kotlinx.serialization.Serializable
import mskb.first.app.entities.Equipment
import mskb.first.app.entities.FireTruckParameter
import kotlinx.datetime.LocalDate

@Serializable
class FireTruckModel(
    val id: Int?, val name: String, val image: ByteArray, val vin: String, val productionYear: Int, val licensePlate: String,
    val operationNumber: String, val type: String, val totalWeight: Int, val horsepower: Int, val numberOfSeats: Int,
    val mileage: Int, @Serializable(with = LocalDateIso8601Serializer::class) val vehicleInspectionExpiryDate: LocalDate,
    @Serializable(with = LocalDateIso8601Serializer::class) val insuranceExpiryDate: LocalDate,
    val parameters: MutableList<FireTruckParameterModel>, val equipment: MutableList<EquipmentModel>
)