package mskb.first.web.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mskb.first.app.entities.enums.CalloutType
import mskb.first.app.services.EquipmentService
import mskb.first.app.utils.toApp
import mskb.first.web.models.EquipmentModel
import mskb.first.web.models.EquipmentParameterModel
import mskb.first.web.models.SectionModel
import java.time.LocalDateTime

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

        get("/filter") {
            val idStart = call.request.queryParameters["idStart"]?.toIntOrNull()
            val idEnd = call.request.queryParameters["idEnd"]?.toIntOrNull()
            val name = call.request.queryParameters["name"]
            val serialNumber = call.request.queryParameters["serialNumber"]
            val quantityStart = call.request.queryParameters["quantityStart"]?.toIntOrNull()
            val quantityEnd = call.request.queryParameters["quantityEnd"]?.toIntOrNull()
            val category = call.request.queryParameters["category"]
            val storageLocation = call.request.queryParameters["storageLocation"]
            call.respond(equipmentService.filterQuery(
                idStart, idEnd, name, serialNumber, quantityStart, quantityEnd, category, storageLocation
            ))
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

        put("/list/change/location/{name}") {
            val storageName = call.parameters["name"]
                ?: return@put call.respondText("No name", status = HttpStatusCode.BadRequest)

            val equipment = call.receive<Array<EquipmentModel>>().map {
                equipmentService.changeLocation(it.toApp(), storageName)
            }

            call.respond(equipment)
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