package mskb.first.app.entities

import java.time.LocalDate

data class Member(
    val id: Int?, val firstname: String, val lastname: String, val birthdate: LocalDate, val birthplace: String,
    val idNumber: String, val address: String, val joiningDate: LocalDate, val role: String, val phoneNumber: String,
    val periodicMedicalExaminationExpiryDate: LocalDate, val isDriver: Boolean, val trainings: MutableList<Training>
)