package com.example.usecase

import com.example.repository.StudentRepository
import org.koin.core.component.KoinComponent

interface SimpleUseCase {
    // 查询A课程比B课程成绩高的所有学生的学号
    fun simple01(courseAName: String, courseBName: String)
}

class SimpleUseCaseImpl: SimpleUseCase, KoinComponent {

    override fun simple01(courseAName: String, courseBName: String) {

    }


}