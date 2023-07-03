package mskb.first.web.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mskb.first.app.entities.enums.CalloutType
import mskb.first.app.services.CalloutService
import mskb.first.app.services.FireTruckService
import mskb.first.app.utils.toApp
import mskb.first.web.models.*
import java.time.LocalDate
import java.time.LocalDateTime

fun Route.fireTruckRoutes() {
    val fireTruckService = FireTruckService()

    route("/fire/truck") {
        get("/get/all") {
            call.respond(fireTruckService.getAll())
        }

        get("/get/archive") {
            call.respond(fireTruckService.getAllWithArchived())
        }
        
        get("/get/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            call.respond(fireTruckService.getById(id))
        }

        get("/filter") {
            val idStart = call.request.queryParameters["idStart"]?.toIntOrNull()
            val idEnd = call.request.queryParameters["idEnd"]?.toIntOrNull()
            val name = call.request.queryParameters["name"]
            val vin = call.request.queryParameters["vin"]
            val productionYearStart = call.request.queryParameters["productionYearStart"]?.toIntOrNull()
            val productionYearEnd = call.request.queryParameters["productionYearEnd"]?.toIntOrNull()
            val licensePlate = call.request.queryParameters["licensePlate"]
            val operationalNumber = call.request.queryParameters["operationalNumber"]
            val type = call.request.queryParameters["type"]
            val totalWeightStart = call.request.queryParameters["totalWeightStart"]?.toIntOrNull()
            val totalWeightEnd = call.request.queryParameters["totalWeightEnd"]?.toIntOrNull()
            val horsepowerStart = call.request.queryParameters["horsepowerStart"]?.toIntOrNull()
            val horsepowerEnd = call.request.queryParameters["horsepowerEnd"]?.toIntOrNull()
            val numberOfSeatsStart = call.request.queryParameters["numberOfSeatsStart"]?.toIntOrNull()
            val numberOfSeatsEnd = call.request.queryParameters["numberOfSeatsEnd"]?.toIntOrNull()
            val mileageStart = call.request.queryParameters["mileageStart"]?.toIntOrNull()
            val mileageEnd = call.request.queryParameters["mileageEnd"]?.toIntOrNull()
            val vehicleInspectionExpiryDateStart = try {
                LocalDate.parse(call.request.queryParameters["vehicleInspectionExpiryDateStart"])
            } catch (_ : Exception) { null }
            val vehicleInspectionExpiryDateEnd = try {
                LocalDate.parse(call.request.queryParameters["vehicleInspectionExpiryDateEnd"])
            } catch (_ : Exception) { null }
            val insuranceExpiryDateStart = try {
                LocalDate.parse(call.request.queryParameters["insuranceExpiryDateStart"])
            } catch (_ : Exception) { null }
            val insuranceExpiryDateEnd = try {
                LocalDate.parse(call.request.queryParameters["insuranceExpiryDateEnd"])
            } catch (_ : Exception) { null }

            call.respond(fireTruckService.filterQuery(
                idStart, idEnd, name, vin, productionYearStart, productionYearEnd, licensePlate, operationalNumber,
                type, totalWeightStart, totalWeightEnd, horsepowerStart, horsepowerEnd, numberOfSeatsStart,
                numberOfSeatsEnd, mileageStart, mileageEnd, vehicleInspectionExpiryDateStart,
                vehicleInspectionExpiryDateEnd, insuranceExpiryDateStart, insuranceExpiryDateEnd
            ))
        }

        post("/save") {
            val fireTruck = call.receive<FireTruckModel>().toApp()
            call.respond(fireTruckService.save(fireTruck))
        }

        post("/save/all") {
            val fireTrucks = call.receive<Array<FireTruckModel>>().map { it.toApp() }
            call.respond(fireTruckService.saveAll(fireTrucks))
        }

        post("/add/parameter/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@post call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            val parameter = call.receive<FireTruckParameterModel>().toApp()
            call.respond(fireTruckService.addParameter(id, parameter))
        }

        put("/update/parameter/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            val parameter = call.receive<FireTruckParameterModel>().toApp()
            call.respond(fireTruckService.updateParameter(id, parameter))
        }

        delete("/delete/parameter/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            val parameterKey = call.receive<String>()
            call.respond(fireTruckService.removeParameter(id, parameterKey))
        }

        put("/update") {
            val fireTruck = call.receive<FireTruckModel>().toApp()
            call.respond(fireTruckService.update(fireTruck))
        }

        delete("/delete/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            call.respond(fireTruckService.delete(id))
        }

        put("/archive/{id}}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            call.respond(fireTruckService.archive(id))
        }
    }
}