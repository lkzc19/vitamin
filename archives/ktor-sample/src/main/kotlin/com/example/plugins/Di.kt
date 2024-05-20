package com.example.plugins

import com.example.graphql.KtorGraphQLServer
import com.example.repository.*
import com.example.usecase.*
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.server.application.*
import org.koin.dsl.module
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger


fun Application.configureKoin() {
    install(Koin) {
        slf4jLogger()

        val simpleModule = module {
            // Dao
            single { StudentRepository() }
            single { TeacherRepository() }
            single { CourseRepository() }
            single { ScoreRepository() }
            single { SimpleRepository() }

            // UseCase
            single<StudentUseCase> { StudentUseCaseImpl() }
            single<TeacherUseCase> { TeacherUseCaseImpl() }
            single<CourseUseCase> { CourseUseCaseImpl() }
            single<ScoreUseCase> { ScoreUseCaseImpl() }
            single<SimpleUseCase> { SimpleUseCaseImpl() }

            single { ObjectMapper() }
            // GraphQL
            single { KtorGraphQLServer.getGraphqlServer(get()) }
        }

        modules(simpleModule)
    }
}