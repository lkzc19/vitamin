package com.example.usecase

import com.example.db.table.Student
import com.example.graphql.param.QueryStudentParam
import com.example.repository.StudentRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

interface StudentUseCase {

    suspend fun add(student: Student)

    suspend fun batchAdd(studentList: List<Student>)

    suspend fun findAll(): List<Student>

    suspend fun findByColumn(param: QueryStudentParam): List<Student>
}

class StudentUseCaseImpl: StudentUseCase, KoinComponent {

    private val studentRepository by inject<StudentRepository>()

    override suspend fun add(student: Student) {
        studentRepository.create(student)
    }

    override suspend fun batchAdd(studentList: List<Student>) {
        studentRepository.batchInsert(studentList)
    }

    override suspend fun findAll(): List<Student> {
        return studentRepository.findAll()
    }

    override suspend fun findByColumn(param: QueryStudentParam): List<Student> {
        return studentRepository.findAll()
    }


}