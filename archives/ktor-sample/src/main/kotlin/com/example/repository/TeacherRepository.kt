package com.example.repository

import com.example.db.table.Teacher
import com.example.db.table.TeacherTable
import com.example.plugins.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class TeacherRepository {

    suspend fun create(teacher: Teacher): Int = dbQuery {
        TeacherTable.insert {
            it[name] = teacher.name
        }[TeacherTable.id]
    }

    suspend fun batchInsert(teacherList: List<Teacher>) = dbQuery {
        TeacherTable.batchInsert(teacherList) { teacher ->
            this[TeacherTable.name] = teacher.name
        }
    }

    suspend fun findAll(): List<Teacher> {
        return dbQuery {
            TeacherTable.selectAll().map { it.toTeacher() }
        }
    }

    private fun ResultRow.toTeacher() = Teacher(
        id = this[TeacherTable.id],
        name = this[TeacherTable.name],
    )
}