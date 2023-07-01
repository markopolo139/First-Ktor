package mskb.first.web.validation

import io.ktor.server.plugins.requestvalidation.*
import kotlinx.datetime.toKotlinLocalDateTime
import mskb.first.web.models.EquipmentModel
import java.time.LocalDateTime

fun RequestValidationConfig.validateEquipment() {
    validate<EquipmentModel> {
        val errors = mutableListOf<String>()

        if (it.id != null)
            if (it.id < 0)
                errors.add("Equipment Id can't be negative if present")

        if (it.name.isBlank())
            errors.add("Equipment name can't be blank")

        if (it.serialNumber.isBlank())
            errors.add("Serial number can't be blank")

        if (it.quantity < 0)
            errors.add("Quantity can't be negative")

        if (it.category.isBlank())
            errors.add("Category can't be blank")

        if (it.storageLocation.isBlank())
            errors.add("Storage location can't be blank")

        if(errors.isEmpty())
            ValidationResult.Valid
        else
            ValidationResult.Invalid(errors)
    }
}