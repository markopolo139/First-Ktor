package mskb.first.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.requestvalidation.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import kotlinx.serialization.Serializable
import mskb.first.app.exceptions.DefaultStorageDeleteException
import mskb.first.app.exceptions.EntityNotFound
import mskb.first.app.exceptions.FeatureNotImplemented
import mskb.first.app.exceptions.NoDefaultStorage

fun Application.configureStatus() {
    install(StatusPages) {
        exception<NoDefaultStorage> {
            call, cause -> call.respond(
                HttpStatusCode.BadRequest, ApiError("Set any storage as default", cause.message!!, emptyList())
            )
        }

        exception<RequestValidationException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                ApiError("Check subErrors", "Invalid validation",
                    cause.reasons.map { SubApiError("Invalid parameter", it) }
                )
            )
        }

        exception<DefaultStorageDeleteException> {
            call, cause -> call.respond(
                HttpStatusCode.BadRequest, ApiError("Change default storage to another one", cause.message!!, emptyList())
            )
        }

        exception<EntityNotFound> {
            call, cause -> call.respond(
                HttpStatusCode.BadRequest, ApiError(
                "Create entity with given id, or check if correct id", cause.message!!, emptyList()
                )
            )
        }

        exception<FeatureNotImplemented> {
            call, cause -> call.respond(HttpStatusCode.NotImplemented, ApiError("Contact with admin", cause.message!!, emptyList()))
        }
    }
}

@Serializable
data class ApiError(val suggestedAction: String, val errorMessage: String, val subErrors: List<SubApiError>)

@Serializable
data class SubApiError(val suggestedAction: String, val errorMessage: String)