package mskb.first.plugins

import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import mskb.first.web.routes.*

fun Application.configureRouting() {
    routing {
        route("/api/v1") {
            calloutRoutes()
            equipmentRoutes()
            fireTruckRoutes()
            memberRoutes()
            storageLocationRoutes()
        }
    }
}
