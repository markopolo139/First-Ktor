package mskb.first

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.tomcat.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.partialcontent.*
import mskb.first.app.persistence.DatabaseFactory
import mskb.first.plugins.configureCors
import mskb.first.plugins.configureLogging
import mskb.first.plugins.configureRouting
import mskb.first.plugins.configureSerialization


//TODO: services for repositories
fun main(args: Array<String>) {
    embeddedServer(Tomcat, environment = commandLineEnvironment(args), configure = {
        configureTomcat = { }
    }).start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()
    install(PartialContent)
    install(AutoHeadResponse)
    configureLogging()
    configureCors()
    configureSerialization()
    configureRouting()
}
