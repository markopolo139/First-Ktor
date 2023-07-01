package mskb.first.web.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mskb.first.app.services.EquipmentService
import mskb.first.app.utils.toApp
import mskb.first.web.models.EquipmentModel
import mskb.first.web.models.EquipmentParameterModel
import mskb.first.web.models.SectionModel

fun Route.equipmentRoutes() {
    val equipmentService = EquipmentService()

    route("/equipment") {
        get("/get/all") {
            call.respond(equipmentService.getAll())
        }

        get("/get/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            call.respond(equipmentService.getById(id))
        }

        get("/get/{storageName}") {
            val storageName = call.parameters["storageName"]
                ?: return@get call.respondText("No name", status = HttpStatusCode.BadRequest)
            call.respond(equipmentService.getByStorageName(storageName))
        }

        post("/save") {
            val equipment = call.receive<EquipmentModel>().toApp()
            call.respond(equipmentService.save(equipment))
        }

        post("/save/all") {
            val equipments = call.receive<Array<EquipmentModel>>().map { it.toApp() }
            call.respond(equipmentService.saveAll(equipments))
        }

        put("/change/location/{name}") {
            val storageName = call.parameters["name"]
                ?: return@put call.respondText("No name", status = HttpStatusCode.BadRequest)
            val equipment = call.receive<EquipmentModel>().toApp()
            call.respond(equipmentService.changeLocation(equipment, storageName))
        }

        post("/add/parameter/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@post call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            val parameter = call.receive<EquipmentParameterModel>().toApp()
            call.respond(equipmentService.addParameter(id, parameter))
        }

        put("/update/parameter/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            val parameter = call.receive<EquipmentParameterModel>().toApp()
            call.respond(equipmentService.updateParameter(id, parameter))
        }

        delete("/delete/parameter/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            val parameterKey = call.receive<String>()
            call.respond(equipmentService.removeParameter(id, parameterKey))
        }

        put("/update") {
            val equipment = call.receive<EquipmentModel>().toApp()
            call.respond(equipmentService.update(equipment))
        }

        delete("/delete/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            call.respond(equipmentService.delete(id))
        }
    }
}