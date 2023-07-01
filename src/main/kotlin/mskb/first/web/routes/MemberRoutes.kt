package mskb.first.web.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mskb.first.app.services.CalloutService
import mskb.first.app.services.MemberService
import mskb.first.app.utils.toApp
import mskb.first.web.models.*

fun Route.memberRoutes() {
    val memberService = MemberService()

    route("/member") {
        get("/get/all") {
            call.respond(memberService.getAll())
        }

        get("/get/archive") {
            call.respond(memberService.getAllWithArchived())
        }

        get("/get/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            call.respond(memberService.getById(id))
        }

        post("/save") {
            val member = call.receive<MemberModel>().toApp()
            call.respond(memberService.save(member))
        }

        post("/save/all") {
            val members = call.receive<Array<MemberModel>>().map { it.toApp() }
            call.respond(memberService.saveAll(members))
        }

        post("/add/training/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@post call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            val training = call.receive<TrainingModel>().toApp()
            call.respond(memberService.saveNewTraining(id, training))
        }

        put("/update/training") {
             val training = call.receive<TrainingModel>().toApp()
            call.respond(memberService.updateTraining(training))
        }

        delete("/delete/training/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            val training = call.receive<TrainingModel>().toApp()
            call.respond(memberService.removeTraining(id, training))
        }

        put("/update") {
            val member = call.receive<MemberModel>().toApp()
            call.respond(memberService.update(member))
        }

        delete("/delete/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            call.respond(memberService.delete(id))
        }

        put("/archive/{id}}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respondText("Invalid id", status = HttpStatusCode.BadRequest)
            call.respond(memberService.archive(id))
        }
    }
}