package mskb.first.app.entities

import java.time.LocalDateTime

data class Section(
    val fireTruck: FireTruck?, var departureDate: LocalDateTime, var returnDate: LocalDateTime, val crew: List<Member>
)