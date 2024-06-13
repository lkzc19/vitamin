package com.example.graphql

import com.expediagroup.graphql.server.execution.GraphQLRequestHandler
import com.expediagroup.graphql.server.execution.GraphQLServer
import com.fasterxml.jackson.databind.ObjectMapper
import io.ktor.server.request.*

class KtorGraphQLServer(
    requestParser: KtorGraphQLRequestParser,
    contextFactory: KtorGraphQLContextFactory,
    requestHandler: GraphQLRequestHandler
): GraphQLServer<ApplicationRequest>(requestParser, contextFactory, requestHandler) {
    companion object {
        fun getGraphqlServer(mapper: ObjectMapper): KtorGraphQLServer {
            val requestParser = KtorGraphQLRequestParser(mapper)
            val contextFactory = KtorGraphQLContextFactory()
            val graphQL = GraphQLSchema().getGraphQLObject()
            val requestHandler = GraphQLRequestHandler(graphQL)

            return KtorGraphQLServer(requestParser, contextFactory, requestHandler)
        }
    }
}