package mskb.first.app.persistence.schema

import mskb.first.app.entities.enums.CalloutType
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime

object CalloutTable: IntIdTable("callouts", "callout_id") {
    val alarmDate = datetime("alarm_date")
    val type = enumerationByName("type", 128, CalloutType::class)
    val location = varchar("location", 256)
    val details = varchar("details", 1250).nullable()
}