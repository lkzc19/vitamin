package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.plugins.defaultheaders.*

fun Application.headerModule() {

    install(DefaultHeaders) {
        header("X-Vitamin", "Vitamin")
        header("X-Drink-Ice", "drink ice")
    }
}