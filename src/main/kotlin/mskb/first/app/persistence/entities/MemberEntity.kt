package mskb.first.app.persistence.entities

import mskb.first.app.persistence.schema.MemberTable
import mskb.first.app.persistence.schema.TrainingTable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID

class MemberEntity(id: EntityID<Int>): IntEntity(id) {
    companion object: IntEntityClass<MemberEntity>(MemberTable)
    var firstname by MemberTable.firstname
    var lastname by MemberTable.lastname
    var birthdate by MemberTable.birthdate
    var birthplace by MemberTable.birthplace
    var idNumber by MemberTable.idNumber
    var address by MemberTable.address
    var joiningDate by MemberTable.joiningDate
    var role by MemberTable.role
    var phoneNumber by MemberTable.phoneNumber
    var periodicMedicalExaminationExpiryDate by MemberTable.periodicMedicalExaminationExpiryDate
    var isDriver by MemberTable.isDriver
    val trainings by TrainingEntity optionalReferrersOn TrainingTable.memberId
}