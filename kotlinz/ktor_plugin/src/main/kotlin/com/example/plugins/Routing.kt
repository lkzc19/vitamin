package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
  routing {
    get("/ping") {
      call.respondText("pong")
    }
  }
  
  routing {
    authenticate {
      get("/ping.auth") {
        call.respondText("pong.auth")
      }
    }
  }
}
