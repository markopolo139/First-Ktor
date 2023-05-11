val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val log4j_version: String by project
val exposed_version: String by project
val h2_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.21"
    id("io.ktor.plugin") version "2.2.1"
    id("org.jetbrains.kotlin.plugin.serialization") version "1.7.21"
}

group = "mskb.first"
version = "0.0.1"
application {
    mainClass.set("mskb.first.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")

    implementation("io.ktor:ktor-server-status-pages:$ktor_version")

    implementation("io.ktor:ktor-server-websockets-jvm:$ktor_version")

    implementation("io.ktor:ktor-server-content-negotiation-jvm:$ktor_version")

    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:$ktor_version")

    implementation("io.ktor:ktor-server-thymeleaf:$ktor_version")
    implementation("org.thymeleaf.extras:thymeleaf-extras-java8time:3.0.4.RELEASE")

    implementation("io.ktor:ktor-server-auth-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auth-jwt-jvm:$ktor_version")

    implementation("io.ktor:ktor-server-tomcat-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-sessions:$ktor_version")

    implementation("io.ktor:ktor-server-request-validation:$ktor_version")
    implementation("io.ktor:ktor-server-auto-head-response:$ktor_version")
    implementation("io.ktor:ktor-server-default-headers-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-cors:$ktor_version")

    implementation("io.ktor:ktor-server-partial-content:$ktor_version")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("io.ktor:ktor-server-test-host:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    implementation("io.ktor:ktor-client-content-negotiation:$ktor_version")

    implementation("org.passay:passay:1.6.2")

    implementation("at.favre.lib:bcrypt:0.9.0")

    implementation("io.ktor:ktor-server-call-logging:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")

    implementation("org.jetbrains.exposed:exposed-core:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-dao:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-jdbc:$exposed_version")
    implementation("org.jetbrains.exposed:exposed-java-time:0.41.1")
    implementation("com.h2database:h2:$h2_version")
    runtimeOnly("org.mariadb.jdbc:mariadb-java-client:2.7.5")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
}