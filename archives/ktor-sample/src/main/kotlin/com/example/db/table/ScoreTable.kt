package com.example.db.table

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table

object ScoreTable: Table() {
    val id = integer("id").autoIncrement()
    val studentId = integer("student_id")
    val courseId = integer("course_id")
    val score = integer("score")

    override val primaryKey = PrimaryKey(id)
}

@Serializable
data class Score(
    val id: Int? = null,
    val studentId: Int,
    val courseId: Int,
    val score: Int
)