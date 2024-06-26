package com.example

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*

fun main() {
    embeddedServer(
        Netty, port = 3000,
        host = "127.0.0.1",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    configureSecurity()
    configureKoin()
    configureHTTP()
    configureSerialization()
//    configureDatabases()
    configureRouting()
}
