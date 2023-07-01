package mskb.first.web.validation

import io.ktor.server.plugins.requestvalidation.*
import mskb.first.web.models.StorageLocationModel
import mskb.first.web.models.TrainingModel

fun RequestValidationConfig.validateTraining() {
    validate<TrainingModel> {
        val errors = mutableListOf<String>()

        if (it.id != null)
            if (it.id.isBlank())
                errors.add("Training Id can't be negative if present")

        if (it.trainingDate > it.expirationDate)
            errors.add("Training date can't be after expiration date")

        if(errors.isEmpty())
            ValidationResult.Valid
        else
            ValidationResult.Invalid(errors)
    }
}