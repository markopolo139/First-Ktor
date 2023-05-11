package mskb.first.app.persistence.entities

import mskb.first.app.persistence.schema.TrainingTable
import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID

class TrainingEntity(id: EntityID<String>): Entity<String>(id) {
    companion object: EntityClass<String, TrainingEntity>(TrainingTable)
    var type by TrainingTable.type
    var trainingDate by TrainingTable.trainingDate
    var expirationDate by TrainingTable.expirationDate

    var member by MemberEntity optionalReferencedOn TrainingTable.memberId
}