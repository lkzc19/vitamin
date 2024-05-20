package com.example.router

import com.example.router.rest.auth
import com.example.router.rest.simpleInit
import io.ktor.server.routing.*

fun Route.restRoute() {
    route("") {
        simpleInit()
        auth()
    }
}