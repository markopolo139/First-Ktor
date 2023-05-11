package mskb.first.app.entities

import mskb.first.app.entities.enums.CalloutType
import java.time.LocalDateTime

data class Callout(
    val id: Int?, val alarmDate: LocalDateTime, var type: CalloutType, var location: String, var details: String?,
    val sections: MutableList<Section>?
)