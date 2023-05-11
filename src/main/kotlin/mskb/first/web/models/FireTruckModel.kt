package mskb.first.web.models

import kotlinx.datetime.serializers.LocalDateIso8601Serializer
import kotlinx.serialization.Serializable
import mskb.first.app.entities.Equipment
import mskb.first.app.entities.FireTruckParameter
import kotlinx.datetime.LocalDate

@Serializable
class FireTruckModel(
    val id: Int?, var name: String, val image: ByteArray, var vin: String, var productionYear: Int, var licensePlate: String,
    var operationNumber: String, var type: String, var totalWeight: Int, var horsepower: Int, var numberOfSeats: Int,
    var mileage: Int, @Serializable(with = LocalDateIso8601Serializer::class) var vehicleInspectionExpiryDate: LocalDate,
    @Serializable(with = LocalDateIso8601Serializer::class) var insuranceExpiryDate: LocalDate,
    val parameters: MutableList<FireTruckParameterModel>, val equipment: MutableList<EquipmentModel>
)