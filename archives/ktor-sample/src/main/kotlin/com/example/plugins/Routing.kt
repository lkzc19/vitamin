package com.example.plugins

import com.example.router.graphqlRouter
import com.example.router.restRoute
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        route("api") {
            restRoute()
            graphqlRouter()
        }
    }
}
