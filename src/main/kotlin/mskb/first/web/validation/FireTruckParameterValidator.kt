package mskb.first.web.validation

import io.ktor.server.plugins.requestvalidation.*
import mskb.first.web.models.EquipmentParameterModel
import mskb.first.web.models.FireTruckParameterModel

fun RequestValidationConfig.validateFireTruckParameter() {
    validate<FireTruckParameterModel> {
        val errors = mutableListOf<String>()

        if (it.key.isBlank())
            errors.add("Fire truck parameter key can't be blank")

        if (it.value.isBlank())
            errors.add("Fire truck parameter value location can't be blank")

        if(errors.isEmpty())
            ValidationResult.Valid
        else
            ValidationResult.Invalid(errors)
    }
}