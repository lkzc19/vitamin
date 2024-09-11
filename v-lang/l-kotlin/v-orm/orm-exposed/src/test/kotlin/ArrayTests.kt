package org.example

import org.example.model.City
import org.example.model.CityTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import kotlin.test.BeforeTest
import kotlin.test.Test

class ArrayTests {

    @BeforeTest
    fun before() {
        DB
    }

    @Test
    fun updateTest(){
        transaction {
            val items = CityTable.selectAll().map {
                City(
                    id = it[CityTable.id].value,
                    name = it[CityTable.name],
                    alias = it[CityTable.alias],
                    history = it[CityTable.history],
                    tag = it[CityTable.tag]
                )
            }
            items.forEach {
                println(it)
            }

            val flatten = items.map { it.tag }.flatten()
            println(flatten)
        }
    }
}