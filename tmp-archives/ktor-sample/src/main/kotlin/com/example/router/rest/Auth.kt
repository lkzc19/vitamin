package com.example.router.rest

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.util.*

fun Route.auth() {

    route("/auth") {
        post("/login") {
            val token = JWT.create()
                .withAudience("zxc")
                .withIssuer("zxc")
                .withClaim("username", "lkzc19")
                .withExpiresAt(Date(System.currentTimeMillis() + 600000))
                .sign(Algorithm.HMAC256("zxc"))
            call.respond(hashMapOf("token" to token))
        }
        authenticate("auth-jwt") {
            get("/test") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("username").asString()
                val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
                call.respondText("Hello, $username! Token is expired at $expiresAt ms.")
            }
        }

    }
}