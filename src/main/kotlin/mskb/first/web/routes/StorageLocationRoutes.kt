package mskb.first.web.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mskb.first.app.services.StorageLocationService
import mskb.first.app.utils.toApp
import mskb.first.web.models.CalloutModel
import mskb.first.web.models.SectionModel
import mskb.first.web.models.StorageLocationModel

fun Route.storageLocationRoutes() {
    val storageLocationService = StorageLocationService()

    route("/storage/location") {
        get("/get/all") {
            call.respond(storageLocationService.getAll())
        }

        get("/get/{name}") {
            val storageName = call.parameters["name"]
                ?: return@get call.respondText("No name", status = HttpStatusCode.BadRequest)
            call.respond(storageLocationService.findByName(storageName))
        }

        get("/get/default") {
            call.respond(storageLocationService.getDefault())
        }

        get("/get/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            call.respond(storageLocationService.getById(id))
        }

        put("/change/default") {
            val storage = call.receive<StorageLocationModel>().toApp()
            call.respond(storageLocationService.changeDefault(storage))
        }

        post("/save") {
            val storage = call.receive<StorageLocationModel>().toApp()
            call.respond(storageLocationService.save(storage))
        }

        post("/save/all") {
            val storage = call.receive<Array<StorageLocationModel>>().map { it.toApp() }
            call.respond(storageLocationService.saveAll(storage))
        }

        put("/update") {
            val storage = call.receive<StorageLocationModel>().toApp()
            call.respond(storageLocationService.update(storage))
        }

        delete("/delete/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            call.respond(storageLocationService.delete(id))
        }
    }
}