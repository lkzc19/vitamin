package com.example.db.table

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

object StudentTable: Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", length = 128)
    val gender = integer("gender")

    override val primaryKey = PrimaryKey(id)
}

@Serializable
data class Student(
    val id: Int? = null,
    val name: String,
    val gender: Int
)