package com.example.repository

import com.example.db.table.Score
import com.example.db.table.ScoreTable
import com.example.plugins.dbQuery
import org.jetbrains.exposed.sql.insert

class ScoreRepository {

    suspend fun create(score: Score): Int = dbQuery {
        ScoreTable.insert {
            it[studentId] = score.studentId
            it[courseId] = score.courseId
            // 同名写全名
            it[ScoreTable.score] = score.score
        }[ScoreTable.id]
    }
}