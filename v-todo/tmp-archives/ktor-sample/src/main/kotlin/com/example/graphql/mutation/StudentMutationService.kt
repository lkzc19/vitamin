package com.example.graphql.mutation

import com.example.db.table.Student
import com.example.repository.StudentRepository
import com.expediagroup.graphql.server.operations.Mutation

class StudentMutationService: Mutation {

    private val studentRepository = StudentRepository()

    suspend fun update(student: Student) {
        student.id?.let { studentRepository.update(it, student) }
    }
}