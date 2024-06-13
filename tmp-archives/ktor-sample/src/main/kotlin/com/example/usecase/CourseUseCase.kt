package com.example.usecase

import com.example.db.table.Course
import com.example.db.table.Student
import com.example.repository.CourseRepository
import org.koin.core.component.KoinComponent

interface CourseUseCase {

    suspend fun add(course: Course)

    suspend fun findAll(): List<Course>
}

class CourseUseCaseImpl: CourseUseCase, KoinComponent {

    private val courseRepository: CourseRepository = CourseRepository()

    override suspend fun add(course: Course) {
        courseRepository.create(course)
    }

    override suspend fun findAll(): List<Course> {
        return courseRepository.findAll()
    }

}