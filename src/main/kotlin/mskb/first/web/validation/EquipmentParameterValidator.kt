package mskb.first.web.validation

import io.ktor.server.plugins.requestvalidation.*
import mskb.first.web.models.EquipmentModel
import mskb.first.web.models.EquipmentParameterModel

fun RequestValidationConfig.validateEquipmentParameter() {
    validate<EquipmentParameterModel> {
        val errors = mutableListOf<String>()

        if (it.key.isBlank())
            errors.add("Equipment parameter key can't be blank")

        if (it.value.isBlank())
            errors.add("Equipment parameter value location can't be blank")

        if(errors.isEmpty())
            ValidationResult.Valid
        else
            ValidationResult.Invalid(errors)
    }
}