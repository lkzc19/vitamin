package org.example

import org.example.model.CityTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

fun main() {

    DB

    transaction {

        val cityId = CityTable.insert {
            it[name] = "三明"
            it[tag] = listOf("三明")
        } get CityTable.id

        println("cityId: $cityId")

        println("CityTable: ${CityTable.selectAll()}")
    }
}