package org.example

import org.example.model.CityTable
import org.example.model.TimeTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DB {
    init {
        Database.connect(
            url = "jdbc:postgresql://localhost:3432/vitamin",
            driver = "org.postgresql.Driver",
            user = "vitamin",
            password = "vitamin"
        )

        transaction {
            SchemaUtils.createMissingTablesAndColumns(CityTable)
            SchemaUtils.createMissingTablesAndColumns(TimeTable)
        }
    }
}