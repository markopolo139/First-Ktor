package mskb.first.app.persistence.schema

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.datetime

object SectionTable: IntIdTable("sections", "section_id") {
    val fireTruckId = reference("fire_truck_id", FireTruckTable, ReferenceOption.NO_ACTION, ReferenceOption.CASCADE)
    val departureTime = datetime("departure_time")
    val returnTime = datetime("return_time")
}