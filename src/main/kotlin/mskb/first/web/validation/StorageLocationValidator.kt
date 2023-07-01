package mskb.first.web.validation

import io.ktor.server.plugins.requestvalidation.*
import mskb.first.web.models.SectionModel
import mskb.first.web.models.StorageLocationModel

fun RequestValidationConfig.validateStorageLocation() {
    validate<StorageLocationModel> {
        val errors = mutableListOf<String>()

        if (it.id != null)
            if (it.id < 0)
                errors.add("Storage Id can't be negative if present")

        if (it.name.isBlank())
            errors.add("Storage location name can't be blank")

        if(errors.isEmpty())
            ValidationResult.Valid
        else
            ValidationResult.Invalid(errors)
    }
}