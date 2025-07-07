package com.example.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sse.*
import io.ktor.sse.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlin.random.Random

fun Application.configureRouting() {
    install(SSE)

    val notifyChannel = Channel<String>()

    routing {
        get("/ping") {
            call.respondText("pong...")
        }

        get("/send") {
            val r = Random.nextInt(1000).toString()
            notifyChannel.send(r)
            call.respondText(r)
        }

        sse("/notify") {
            while (true) {
                val receive = notifyChannel.receive()
                send(ServerSentEvent("this is SSE #$receive"))
                delay(1000)
            }
        }
    }
}
