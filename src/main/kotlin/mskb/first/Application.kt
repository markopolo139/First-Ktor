package mskb.first

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.tomcat.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.partialcontent.*
import mskb.first.app.persistence.DatabaseFactory
import mskb.first.plugins.configureRouting

fun main(args: Array<String>){
    embeddedServer(Tomcat, environment = commandLineEnvironment(args), configure = {
        configureTomcat = {
            //Here can set up tomcat
        }
    }).start(wait = true)
}


fun Application.module() {
    DatabaseFactory.init()
    install(PartialContent)
    install(AutoHeadResponse)
    configureRouting()
}
