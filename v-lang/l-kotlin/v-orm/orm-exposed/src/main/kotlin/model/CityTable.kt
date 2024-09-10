package org.example.model

import org.jetbrains.exposed.dao.id.IntIdTable

object CityTable: IntIdTable() {
    val name = varchar("name", 50)
    val alias = varchar("alias", 50)
    val history = long("history")
    val tag = array<String>("tag", 5).default(emptyList())

    val uniqueIndex = uniqueIndex("unique_name_alias", name, alias)
}

data class City(
    val id: Int,
    val name: String,
    val alias: String,
    val history: Long,
    val tag: List<String>,
)
