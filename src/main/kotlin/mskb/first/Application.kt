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

fun main(args: Array<String>){
    embeddedServer(Tomcat, environment = commandLineEnvironment(args), configure = {
        configureTomcat = {  }
    }).start(wait = true)
}

//TODO:
// when adding equipment, also add it to storage location equipment list
// when creating, deleting fire truck add it as storage location
// storage location also have default, where all equipment from deleted fire truck will go
// storage location will also have standard repository
// adding equipment changes only it's storage location
// repo for equipment (add getting equipment by location)
// delete equipment from fire truck returns it to default location
// archivization option for fire truck, member (when deleting setting it) (it will just be additional column in database)
fun Application.module() {
    DatabaseFactory.init()
    install(PartialContent)
    install(AutoHeadResponse)
    configureLogging()
    configureCors()
    configureSerialization()
    configureRouting()
}
