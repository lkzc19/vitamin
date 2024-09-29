package com.example.repository

import com.example.db.table.Student
import com.example.db.table.StudentTable
import com.example.graphql.param.QueryStudentParam
import com.example.plugins.dbQuery
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class StudentRepository {

    suspend fun create(student: Student): Int = dbQuery {
        StudentTable.insert {
            it[name] = student.name
            it[gender] = student.gender
        }[StudentTable.id]
    }

    suspend fun batchInsert(studentList: List<Student>) = dbQuery {
        StudentTable.batchInsert(studentList) {student ->
            this[StudentTable.name] = student.name
            this[StudentTable.gender] = student.gender
        }
    }

    suspend fun findAll(): List<Student> {
        return dbQuery {
            StudentTable.selectAll().map { it.toStudent() }
        }
    }

    suspend fun findById(id: Int): Student? {
        return dbQuery {
            StudentTable.select { StudentTable.id eq id }
                .map { it.toStudent() }
                .singleOrNull()
        }
    }

    suspend fun findNameById(id: Int): String? {
        return dbQuery {
            StudentTable.slice(StudentTable.name)
                .select { StudentTable.id eq id }
                .map { it[StudentTable.name] }
                .singleOrNull()
        }
    }

    suspend fun findByColumn(param: QueryStudentParam) {

    }

    suspend fun update(id: Int, student: Student) {
        dbQuery {
            StudentTable.update({ StudentTable.id eq id }) {
                it[name] = student.name
            }
        }
    }

    suspend fun delete(id: Int) {
        dbQuery {
            StudentTable.deleteWhere {
                StudentTable.id eq id
            }
        }
    }

    private fun ResultRow.toStudent() = Student(
        id = this[StudentTable.id],
        name = this[StudentTable.name],
        gender = this[StudentTable.gender]
    )
}