package com.example.graphql.query

import com.example.db.table.Student
import com.example.graphql.param.QueryStudentParam
import com.example.usecase.StudentUseCaseImpl
import com.expediagroup.graphql.server.operations.Query

class StudentQueryService: Query {

    private val studentUseCase = StudentUseCaseImpl()

    suspend fun findStudent(param: QueryStudentParam): List<Student> {
        return studentUseCase.findByColumn(param)
    }
}