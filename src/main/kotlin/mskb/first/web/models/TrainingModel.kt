package mskb.first.web.models

import kotlinx.datetime.serializers.LocalDateIso8601Serializer
import kotlinx.serialization.Serializable
import mskb.first.app.entities.enums.TrainingType
import kotlinx.datetime.LocalDate

@Serializable
class TrainingModel(
    val id: String?, val type: TrainingType,
    @Serializable(with = LocalDateIso8601Serializer::class) val trainingDate: LocalDate,
    @Serializable(with = LocalDateIso8601Serializer::class) val expirationDate: LocalDate
)