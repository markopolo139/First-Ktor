package mskb.first.app.persistence.entities

import mskb.first.app.persistence.schema.CalloutTable
import mskb.first.app.persistence.schema.SectionTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class CalloutEntity(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<CalloutEntity>(CalloutTable)
    var alarmDate by CalloutTable.alarmDate
    var type by CalloutTable.type
    var location by CalloutTable.location
    var details by CalloutTable.details
    val sections by SectionEntity referrersOn SectionTable.calloutId
}