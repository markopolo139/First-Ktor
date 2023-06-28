package mskb.first.web.models

import kotlinx.datetime.LocalDate
import kotlinx.datetime.serializers.LocalDateIso8601Serializer
import kotlinx.serialization.Serializable

@Serializable
class MemberModel(
    val id: Int?, val firstname: String, val lastname: String,
    @Serializable(with = LocalDateIso8601Serializer::class) val birthdate: LocalDate, val birthplace: String,
    val idNumber: String, val address: String, @Serializable(with = LocalDateIso8601Serializer::class) val joiningDate: LocalDate,
    val role: String, val phoneNumber: String, @Serializable(with = LocalDateIso8601Serializer::class) val periodicMedicalExaminationExpiryDate: LocalDate,
    val isDriver: Boolean, val trainings: MutableList<TrainingModel>
)