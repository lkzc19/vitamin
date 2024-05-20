package com.example.repository

import com.example.db.table.CourseTable
import com.example.db.table.ScoreTable
import com.example.db.table.StudentTable
import com.example.plugins.dbQuery
import org.jetbrains.exposed.sql.JoinType
import org.jetbrains.exposed.sql.alias
import org.jetbrains.exposed.sql.select

class SimpleRepository {

    // 查询A课程比B课程成绩高的所有学生的学号
    suspend fun simple01(courseAName: String, courseBName: String) = dbQuery {
//        val s1 = ScoreTable.alias("s1")
//        val s2 = ScoreTable.alias("s2")
//        StudentTable.join(ScoreTable, JoinType.INNER, additionalConstraint = {
//            StudentTable.id eq ScoreTable.studentId
//        }).select {
//
//        }
    }
}