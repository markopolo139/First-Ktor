package mskb.first.web.models

import kotlinx.datetime.serializers.LocalDateTimeIso8601Serializer
import kotlinx.serialization.Serializable
import mskb.first.app.entities.FireTruck
import mskb.first.app.entities.Member
import kotlinx.datetime.LocalDateTime

@Serializable
class SectionModel(
    val fireTruck: FireTruckModel?,
    @Serializable(with = LocalDateTimeIso8601Serializer::class) val departureDate: LocalDateTime,
    @Serializable(with = LocalDateTimeIso8601Serializer::class) val returnDate: LocalDateTime, val crew: List<MemberModel>
)