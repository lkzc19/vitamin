package com.example.usecase

import com.example.db.table.Teacher
import com.example.repository.TeacherRepository
import org.koin.core.component.KoinComponent

interface TeacherUseCase {

    suspend fun add(teacher: Teacher)

    suspend fun batchAdd(teacherList: List<Teacher>)

    suspend fun findAll(): List<Teacher>
}

class TeacherUseCaseImpl: TeacherUseCase, KoinComponent {

    private val teacherRepository: TeacherRepository = TeacherRepository()

    override suspend fun add(teacher: Teacher) {
        teacherRepository.create(teacher)
    }

    override suspend fun batchAdd(teacherList: List<Teacher>) {
        teacherRepository.batchInsert(teacherList)
    }

    override suspend fun findAll(): List<Teacher> {
        return teacherRepository.findAll()
    }
}