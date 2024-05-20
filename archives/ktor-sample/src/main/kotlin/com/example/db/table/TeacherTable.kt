package com.example.db.table

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

object TeacherTable: Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", length = 128)

    override val primaryKey = PrimaryKey(id)
}

@Serializable
data class Teacher(
    val id: Int? = null,
    val name: String
)