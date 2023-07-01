package mskb.first.web.validation

import io.ktor.server.plugins.requestvalidation.*
import kotlinx.datetime.toKotlinLocalDate
import mskb.first.web.models.SectionModel
import java.time.LocalDate

fun RequestValidationConfig.validateSection() {
    validate<SectionModel> {
        val errors = mutableListOf<String>()

        if (it.id != null)
            if (it.id < 0)
                errors.add("Section Id can't be negative if present")

        if (it.departureDate > it.returnDate)
            errors.add("Departure date can't be after return date")

        if(errors.isEmpty())
            ValidationResult.Valid
        else
            ValidationResult.Invalid(errors)
    }
}