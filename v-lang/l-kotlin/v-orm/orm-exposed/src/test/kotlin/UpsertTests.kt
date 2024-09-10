package org.example

import org.example.model.CityTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update
import org.jetbrains.exposed.sql.upsert
import kotlin.test.BeforeTest
import kotlin.test.Test

class UpsertTests {

    @BeforeTest
    fun before() {
        DB
    }

    @Test
    fun upsertTest(){
        transaction {
            val cityId = CityTable.insert {
                it[name] = "三明"
                it[alias] = "三明"
                it[history] = 4000
//                it[tag] = listOf("三明")
            } get CityTable.id
            println("cityId: $cityId")

            CityTable.upsert(
                CityTable.name, CityTable.alias,
                onUpdate = { it[CityTable.history] = 5000 }
            ) {
                it[name] = "三明"
                it[alias] = "三明"
                it[history] = 5000
//                it[tag] = listOf("尤溪", "明溪", "汤川")
            }
        }
    }

    @Test
    fun upsert1Test(){
        transaction {
            CityTable.upsert(
                CityTable.name, CityTable.alias,
                onUpdate = {
                    it[CityTable.history] = 5000
                    it[CityTable.tag] = listOf("尤溪", "明溪", "汤川")
                }
            ) {
                it[name] = "三明"
                it[alias] = "三明"
                it[history] = 5000
                it[tag] = listOf("尤溪", "明溪", "汤川")
            }
        }
    }

    @Test
    fun upsert2Test(){
        transaction {
            CityTable.upsert(
                CityTable.name, CityTable.alias,
                onUpdate = {
                    it[CityTable.history] = 5000
                    it[CityTable.tag] = listOf("尤溪", "明溪", "汤川")
                }
            ) {
                it[name] = "四明"
                it[alias] = "四明"
                it[history] = 5000
                it[tag] = listOf("尤溪", "明溪", "汤川")
            }
        }
    }

    @Test
    fun updateTest(){
        transaction {
            CityTable.update({ (CityTable.name eq "三明") and (CityTable.alias eq "三明") }) {
                it[history] = 6000
                it[tag] = listOf("尤溪", "明溪", "汤川")
            }
        }
    }
}