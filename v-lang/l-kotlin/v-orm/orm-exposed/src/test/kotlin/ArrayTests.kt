package org.example

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
            CityTable.selectAll().map {  }
        }
    }
}