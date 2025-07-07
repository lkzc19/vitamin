package com.example.plugins

import com.example.router.pingRouter
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.routerModule() {

    routing {
        pingRouter()
    }
}
