package com.example.repository

import com.example.db.table.Course
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import kotlin.test.BeforeTest
import kotlin.test.Test

class CourseRepositoryTest {

    @BeforeTest
    fun init() {
        Database.connect(
            url = "jdbc:postgresql://127.0.0.1:5432/simple",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "111111"
        )
    }

    @Test
    fun create(): Unit = runBlocking {
        CourseRepository().create(Course(name = "抽卡概率学", teacherId = 1))
        CourseRepository().create(Course(name = "元素论", teacherId = 1))
        CourseRepository().create(Course(name = "提瓦特指南", teacherId = 1))
        CourseRepository().create(Course(name = "丘丘语学", teacherId = 1))
    }
}