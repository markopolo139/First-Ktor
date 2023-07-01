package mskb.first.web.validation

import io.ktor.server.plugins.requestvalidation.*
import kotlinx.datetime.toKotlinLocalDate
import mskb.first.web.models.MemberModel
import java.time.LocalDate

fun RequestValidationConfig.validateMember() {
    validate<MemberModel> {
        val errors = mutableListOf<String>()

        if (it.id != null)
            if (it.id < 0)
                errors.add("Member Id can't be negative if present")

        if (it.firstname.isBlank())
            errors.add("Firstname can't be blank")

        if (it.lastname.isBlank())
            errors.add("Lastname can't be blank")

        if (it.birthdate > LocalDate.now().toKotlinLocalDate())
            errors.add("Birthdate can't be later than today")

        if (it.idNumber.isBlank())
            errors.add("Id number plate can't be blank")

        if (it.address.isBlank())
            errors.add("Address can't be blank")

        if (it.joiningDate < it.birthdate)
            errors.add("Can't join before being born")

        if (it.role.isBlank())
            errors.add("Role can't be blank")

        if (it.phoneNumber.isBlank())
            errors.add("Phone number can't be blank")

        if(errors.isEmpty())
            ValidationResult.Valid
        else
            ValidationResult.Invalid(errors)
    }
}