package mskb.first.app.persistence.schema

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

object MemberTable: IntIdTable("members", "member_id") {
    val firstname = varchar("firstname", 256)
    val lastname = varchar("lastname", 256)
    val birthdate = date("birthdate")
    val birthplace = varchar("birthplace", 256)
    val idNumber = varchar("id_number", 256)
    val address = varchar("address", 256)
    val joiningDate = date("joining_date")
    val role = varchar("role", 128)
    val phoneNumber = varchar("phone_number", 20)
    val periodicMedicalExaminationExpiryDate = date("periodic_medical_examination_expiry_date")
    val isDriver = bool("driver")
    val archived = bool("archived")
}