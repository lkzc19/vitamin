package com.example.plugins

import com.example.db.table.CourseTable
import com.example.db.table.ScoreTable
import com.example.db.table.StudentTable
import com.example.db.table.TeacherTable
import io.ktor.server.application.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabases() {
    Database.connect(
        url = "jdbc:postgresql://127.0.0.1:5432/simple",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "111111"
    )

    transaction {
        SchemaUtils.create(CourseTable)
        SchemaUtils.create(ScoreTable)
        SchemaUtils.create(StudentTable)
        SchemaUtils.create(TeacherTable)
    }
}

suspend fun <T> dbQuery(block: () -> T): T =
    withContext(Dispatchers.IO) {
        transaction {
            addLogger(StdOutSqlLogger)
            block()
        }
    }