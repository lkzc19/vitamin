package com.example.plugins

import io.ktor.http.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.serialization.json.*

fun Application.configureHTTP() {
    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }
}

suspend inline fun <reified T: Any> ApplicationCall.ok(data: T? = null) {
    respond(
        status = HttpStatusCode.OK,
        message = Json.encodeToJsonElement(data)
    )
}

suspend inline fun ApplicationCall.fail(errMsg: String?) {
    respond(
        status = HttpStatusCode.InternalServerError,
        buildJsonObject {
            put("errMsg", errMsg)
            // error code
            // traceId
        }
    )
}
