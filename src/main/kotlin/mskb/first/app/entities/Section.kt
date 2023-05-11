package mskb.first.app.entities

import java.time.LocalDateTime

data class Section(
    val fireTruck: FireTruck?, val departureDate: LocalDateTime, val returnDate: LocalDateTime, val crew: List<Member>
)