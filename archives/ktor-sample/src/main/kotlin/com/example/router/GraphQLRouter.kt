package com.example.router

import com.example.graphql.KtorGraphQLServer
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GraphQLServerController : KoinComponent {
    private val mapper by inject<ObjectMapper>()
    private val ktorGraphQLServer by inject<KtorGraphQLServer>()
    suspend fun handle(applicationCall: ApplicationCall) {
        val result = ktorGraphQLServer.execute(applicationCall.request)
        if (result != null) {
            val json = mapper.writeValueAsString(result)
            applicationCall.response.call.respondText(json, ContentType.Application.Json)
        } else {
            applicationCall.response.call.respond(HttpStatusCode.BadRequest, "Invalid request")
        }
    }
}

fun Route.graphqlRouter() {
    authenticate("auth-jwt") {
        post("graphql") {
            GraphQLServerController().handle(this.call)
        }
    }
}