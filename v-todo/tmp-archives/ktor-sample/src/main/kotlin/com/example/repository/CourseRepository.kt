package com.example.repository

import com.example.db.table.Course
import com.example.db.table.CourseTable
import com.example.plugins.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class CourseRepository {

    suspend fun create(course: Course): Int = dbQuery {
        CourseTable.insert {
            it[name] = course.name
            it[teacherId] = course.teacherId
        }[CourseTable.id]
    }

    suspend fun findAll(): List<Course> {
        return dbQuery {
            CourseTable.selectAll().map { it.toCourse() }
        }
    }

    private fun ResultRow.toCourse() = Course(
        id = this[CourseTable.id],
        name = this[CourseTable.name],
        teacherId = this[CourseTable.teacherId]
    )
}