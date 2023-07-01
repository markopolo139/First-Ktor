package mskb.first.web.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mskb.first.app.services.CalloutService
import mskb.first.app.services.FireTruckService
import mskb.first.app.utils.toApp
import mskb.first.web.models.*

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