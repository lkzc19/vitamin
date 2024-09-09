package org.example.model

import org.jetbrains.exposed.dao.id.IntIdTable

object CityTable: IntIdTable() {
    val name = varchar("name", 50)
    val tag = array<String>("tag", 5).nullable()
}

data class City(
    val id: Int,
    val name: String,
    val tag: List<String>,
)
