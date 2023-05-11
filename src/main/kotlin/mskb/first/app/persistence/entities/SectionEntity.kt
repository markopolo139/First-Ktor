package mskb.first.app.persistence.entities

import mskb.first.app.persistence.schema.SectionMemberTable
import mskb.first.app.persistence.schema.SectionTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class SectionEntity(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<SectionEntity>(SectionTable)
    var departureTime by SectionTable.departureTime
    var returnTime by SectionTable.returnTime
    val crew by MemberEntity via SectionMemberTable
    var fireTruck by FireTruckEntity referencedOn SectionTable.fireTruckId

    var callout by CalloutEntity optionalReferencedOn SectionTable.calloutId
}