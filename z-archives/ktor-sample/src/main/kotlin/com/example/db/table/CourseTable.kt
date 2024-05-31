package com.example.db.table

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

object CourseTable: Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", length = 128)
    val teacherId = integer("teacher_id")

    override val primaryKey = PrimaryKey(id)
}

@Serializable
data class Course(
    val id: Int? = null,
    val name: String,
    val teacherId: Int
)