package org.example

import org.example.model.TimeTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.Instant
import kotlin.test.BeforeTest
import kotlin.test.Test

class TimeTests {

    @BeforeTest
    fun before() {
        DB
    }

    @Test
    fun updateTest(){
        transaction {
            TimeTable.insert {
                it[testTime] = Instant.now()
            }
        }
    }
}