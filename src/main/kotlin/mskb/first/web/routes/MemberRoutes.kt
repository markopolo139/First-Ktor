package mskb.first.web.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mskb.first.app.entities.enums.CalloutType
import mskb.first.app.entities.enums.TrainingType
import mskb.first.app.services.CalloutService
import mskb.first.app.services.MemberService
import mskb.first.app.utils.toApp
import mskb.first.web.models.*
import java.time.LocalDate
import java.time.LocalDateTime

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

        get("/filter") {
            val idStart = call.request.queryParameters["idStart"]?.toIntOrNull()
            val idEnd = call.request.queryParameters["idEnd"]?.toIntOrNull()
            val firstname = call.request.queryParameters["firstname"]
            val lastname = call.request.queryParameters["lastname"]
            val birthdateStart = try {
                LocalDate.parse(call.request.queryParameters["birthdateStart"])
            } catch (_ : Exception) { null }
            val birthdateEnd = try {
                LocalDate.parse(call.request.queryParameters["birthdateEnd"])
            } catch (_ : Exception) { null }
            val birthplace = call.request.queryParameters["birthplace"]
            val idNumber = call.request.queryParameters["idNumber"]
            val address = call.request.queryParameters["address"]
            val joiningDateStart = try {
                LocalDate.parse(call.request.queryParameters["joiningDateStart"])
            } catch (_ : Exception) { null }
            val joiningDateEnd = try {
                LocalDate.parse(call.request.queryParameters["joiningDateEnd"])
            } catch (_ : Exception) { null }
            val role = call.request.queryParameters["role"]
            val phoneNumber = call.request.queryParameters["phoneNumber"]
            val periodicMedicalExaminationExpiryDateStart = try {
                LocalDate.parse(call.request.queryParameters["periodicMedicalExaminationExpiryDateStart"])
            } catch (_ : Exception) { null }
            val periodicMedicalExaminationExpiryDateEnd = try {
                LocalDate.parse(call.request.queryParameters["periodicMedicalExaminationExpiryDateEnd"])
            } catch (_ : Exception) { null }
            val isDriver = call.request.queryParameters["isDriver"]?.toBooleanStrictOrNull()
            val trainingType = try {
                TrainingType.valueOf(call.request.queryParameters["trainingType"]!!)
            } catch (_ : Exception) { null }
            val trainingDateStart = try {
                LocalDate.parse(call.request.queryParameters["trainingDateStart"])
            } catch (_ : Exception) { null }
            val trainingDateEnd = try {
                LocalDate.parse(call.request.queryParameters["trainingDateEnd"])
            } catch (_ : Exception) { null }
            val trainingExpirationDateStart = try {
                LocalDate.parse(call.request.queryParameters["trainingExpirationDateStart"])
            } catch (_ : Exception) { null }
            val trainingExpirationDateEnd = try {
                LocalDate.parse(call.request.queryParameters["trainingExpirationDateEnd"])
            } catch (_ : Exception) { null }
            call.respond(memberService.filterQuery(
                idStart, idEnd, firstname, lastname, birthdateStart, birthdateEnd, birthplace, idNumber, address,
                joiningDateStart, joiningDateEnd, role, phoneNumber, periodicMedicalExaminationExpiryDateStart,
                periodicMedicalExaminationExpiryDateEnd, isDriver, trainingType, trainingDateStart, trainingDateEnd,
                trainingExpirationDateStart, trainingExpirationDateEnd
            ))
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