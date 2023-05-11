package mskb.first.web.models

import kotlinx.serialization.Serializable
import mskb.first.app.entities.enums.CalloutType
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.serializers.LocalDateTimeIso8601Serializer

@Serializable
class CalloutModel(
    val id: Int?, @Serializable(with = LocalDateTimeIso8601Serializer::class) val alarmDate: LocalDateTime,
    var type: CalloutType, var location: String, var details: String?, val sections: MutableList<SectionModel>
)