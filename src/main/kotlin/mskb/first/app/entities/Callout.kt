package mskb.first.app.entities

import mskb.first.app.entities.enums.CalloutType
import java.time.LocalDateTime

data class Callout(
    val id: Int, val alarmDate: LocalDateTime, val type: CalloutType, val location: String, val details: String?,
    val sections: MutableList<Section>
)