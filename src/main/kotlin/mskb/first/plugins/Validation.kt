package mskb.first.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import mskb.first.web.validation.*

fun Application.configureValidation() {
    install(RequestValidation) {
        validateCallout()
        validateEquipment()
        validateEquipmentParameter()
        validateFireTruck()
        validateFireTruckParameter()
        validateMember()
        validateSection()
        validateStorageLocation()
        validateTraining()
    }
}