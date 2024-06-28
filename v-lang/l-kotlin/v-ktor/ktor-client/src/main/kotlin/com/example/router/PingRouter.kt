package com.example.router

import com.example.utils.Cache
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.time.delay
import java.time.Instant
import kotlin.time.Duration.Companion.seconds
import kotlin.time.toJavaDuration

fun Route.pingRouter() {
    val logger = KotlinLogging.logger {}

    route("") {
        get("/ping") { call.respondText("pong...") }

        get("/log") {
            logger.trace { "trace..." }
            logger.debug { "debug..." }
            logger.info { "info..." }
            logger.warn { "warn..." }
            logger.error { "error..." }
            call.respondText("log...")
        }

        get("401") {
            call.respondText(text = "401...", status = HttpStatusCode.Unauthorized)
        }

        get("retry401") {
            if (Cache.lastTime != 0L) {
                val epochSecond = Instant.ofEpochMilli((System.currentTimeMillis() - Cache.lastTime)).epochSecond
                logger.info { "离上次请求过了 $epochSecond 秒" }
            }
            Cache.lastTime = System.currentTimeMillis()
            logger.info { "header.retry: ${call.request.headers["X-RETRY-COUNT"]}" }
            logger.info { "counter: ${Cache.counter}" }
            if (Cache.counter++ < 3) {
                call.respondText(text = "401...", status = HttpStatusCode.Unauthorized)
                return@get
            }
            Cache.counter = 0
            call.respondText(text = "200...")
        }

        get("sleep10s") {
            delay(10.seconds.toJavaDuration())
            call.respondText(text = "sleep10s...")
        }
    }
}