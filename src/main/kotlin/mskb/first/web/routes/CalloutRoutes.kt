package mskb.first.web.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mskb.first.app.services.CalloutService
import mskb.first.app.utils.toApp
import mskb.first.web.models.CalloutModel
import mskb.first.web.models.SectionModel

fun Route.calloutRoutes() {
    val calloutService = CalloutService()

    route("/callouts") {
        get("/get/all") {
            call.respond(calloutService.getAll())
        }

        get("/get/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            call.respond(calloutService.getById(id))
        }

        post("/save") {
            val callout = call.receive<CalloutModel>().toApp()
            call.respond(calloutService.save(callout))
        }

        post("/save/all") {
            val callouts = call.receive<Array<CalloutModel>>().map { it.toApp() }
            call.respond(calloutService.saveAll(callouts))
        }

        post("/add/section/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@post call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            val section = call.receive<SectionModel>().toApp()
            call.respond(calloutService.addSection(id, section))
        }

        put("/update/section") {
            val section = call.receive<SectionModel>().toApp()
            call.respond(calloutService.updateSection(section))
        }

        delete("/delete/section/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            val section = call.receive<SectionModel>().toApp()
            call.respond(calloutService.removeSection(id, section))
        }

        put("/update") {
            val callout = call.receive<CalloutModel>().toApp()
            call.respond(calloutService.update(callout))
        }

        delete("/delete/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            call.respond(calloutService.delete(id))
        }
    }
}