package mskb.first.web.models

import kotlinx.datetime.LocalDate
import kotlinx.datetime.serializers.LocalDateIso8601Serializer
import kotlinx.serialization.Serializable

@Serializable
class MemberModel(
    val id: Int?, var firstname: String, var lastname: String,
    @Serializable(with = LocalDateIso8601Serializer::class) var birthdate: LocalDate, var birthplace: String,
    var idNumber: String, var address: String, @Serializable(with = LocalDateIso8601Serializer::class) var joiningDate: LocalDate,
    var role: String, var phoneNumber: String, @Serializable(with = LocalDateIso8601Serializer::class) var periodicMedicalExaminationExpiryDate: LocalDate,
    var isDriver: Boolean, val trainings: MutableList<TrainingModel>
)