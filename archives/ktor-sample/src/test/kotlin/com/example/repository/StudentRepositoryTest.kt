package com.example.repository

import com.example.db.table.Student
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import kotlin.test.*

class StudentRepositoryTest {

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
        StudentRepository().create(Student(name = "胡桃", gender = 0))
        StudentRepository().create(Student(name = "钟离", gender = 1))
        StudentRepository().create(Student(name = "温迪", gender = 1))
        StudentRepository().create(Student(name = "迪奥娜", gender = 0))
    }

    @Test
    fun findById(): Unit = runBlocking {
        val student = StudentRepository().findById(1)
        println(student)
    }

    @Test
    fun findNameById(): Unit = runBlocking {
        val name = StudentRepository().findNameById(1)
        println(name)
    }

    @Test
    fun update(): Unit = runBlocking {
        StudentRepository().update(1, Student(name = "云瑾", gender = 0))
        val student = StudentRepository().findById(1)
        println(student)
    }

    @Test
    fun delete(): Unit = runBlocking {
        StudentRepository().delete(1)
        val student = StudentRepository().findById(1)
        println(student)
    }
}