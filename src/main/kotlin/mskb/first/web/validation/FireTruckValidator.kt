package mskb.first.web.validation

import io.ktor.server.plugins.requestvalidation.*
import mskb.first.web.models.EquipmentModel
import mskb.first.web.models.FireTruckModel

fun RequestValidationConfig.validateFireTruck() {
    validate<FireTruckModel> {
        val errors = mutableListOf<String>()

        if (it.id != null)
            if (it.id < 0)
                errors.add("Fire truck Id can't be negative if present")

        if (it.name.isBlank())
            errors.add("Fire truck name can't be blank")

        if (it.vin.isBlank())
            errors.add("Vin can't be blank")

        if (it.productionYear < 0)
            errors.add("Production year can't be negative")

        if (it.licensePlate.isBlank())
            errors.add("License plate can't be blank")

        if (it.operationNumber.isBlank())
            errors.add("Operation number can't be blank")

        if (it.type.isBlank())
            errors.add("Fire truck type can't be blank")

        if (it.totalWeight < 0)
            errors.add("Weight can't be negative")

        if (it.horsepower < 0)
            errors.add("Horsepower can't be negative")

        if (it.numberOfSeats < 0)
            errors.add("Number of seats can't be negative")

        if (it.mileage < 0)
            errors.add("Mileage can't be negative")

        if(errors.isEmpty())
            ValidationResult.Valid
        else
            ValidationResult.Invalid(errors)
    }
}