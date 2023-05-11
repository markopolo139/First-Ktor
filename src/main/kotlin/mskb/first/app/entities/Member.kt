package mskb.first.app.entities

import java.time.LocalDate

data class Member(
    val id: Int?, var firstname: String, var lastname: String, var birthdate: LocalDate, var birthplace: String,
    var idNumber: String, var address: String, var joiningDate: LocalDate, var role: String, var phoneNumber: String,
    var periodicMedicalExaminationExpiryDate: LocalDate, var isDriver: Boolean, val trainings: MutableList<Training>?
)