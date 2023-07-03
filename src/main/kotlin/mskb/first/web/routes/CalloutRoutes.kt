package mskb.first.web.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.datetime.toLocalDateTime
import mskb.first.app.entities.enums.CalloutType
import mskb.first.app.services.CalloutService
import mskb.first.app.utils.toApp
import mskb.first.web.models.CalloutModel
import mskb.first.web.models.SectionModel
import java.time.LocalDateTime

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

        get("/filter") {
            val idStart = call.request.queryParameters["idStart"]?.toIntOrNull()
            val idEnd = call.request.queryParameters["idEnd"]?.toIntOrNull()
            val alarmDateStart = try {
                LocalDateTime.parse(call.request.queryParameters["alarmDateStart"])
            } catch (_ : Exception) { null }
            val alarmDateEnd = try {
                LocalDateTime.parse(call.request.queryParameters["alarmDateEnd"])
            } catch (_ : Exception) { null }
            val type = try {
                CalloutType.valueOf(call.request.queryParameters["type"]!!)
            } catch (_ : Exception) { null }
            val location = call.request.queryParameters["location"]

            call.respond(calloutService.filterQuery(
                idStart, idEnd, alarmDateStart, alarmDateEnd, type, location
            ))
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