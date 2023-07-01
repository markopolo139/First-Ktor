package mskb.first.web.validation

import io.ktor.server.plugins.requestvalidation.*
import kotlinx.datetime.toKotlinLocalDateTime
import mskb.first.web.models.CalloutModel
import java.time.LocalDateTime

fun RequestValidationConfig.validateCallout() {
    validate<CalloutModel> {
        val errors = mutableListOf<String>()

        if (it.id != null)
            if (it.id < 0)
                errors.add("Callout Id can't be negative if present")

        if (it.location.isBlank())
            errors.add("Callout location can't be blank")

        if (it.alarmDate > LocalDateTime.now().toKotlinLocalDateTime())
            errors.add("Can't add future time of an alarm")

        if(errors.isEmpty())
            ValidationResult.Valid
        else
            ValidationResult.Invalid(errors)
    }
}