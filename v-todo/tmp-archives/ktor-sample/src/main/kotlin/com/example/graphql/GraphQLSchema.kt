package com.example.graphql

import com.example.graphql.mutation.StudentMutationService
import com.example.graphql.query.StudentQueryService
import com.expediagroup.graphql.generator.SchemaGeneratorConfig
import com.expediagroup.graphql.generator.TopLevelObject
import com.expediagroup.graphql.generator.toSchema
import graphql.GraphQL

class GraphQLSchema {
    private val config = SchemaGeneratorConfig(
        supportedPackages = listOf("com.example", "kotlin"),
    )

    private val queries = listOf(
        TopLevelObject(StudentQueryService()),
    )

//    private val mutations = listOf(
//        TopLevelObject(StudentMutationService()),
//    )

    private val graphQLSchema = toSchema(config, queries)

    fun getGraphQLObject(): GraphQL =
        GraphQL
            .newGraphQL(graphQLSchema)
            .build()
}