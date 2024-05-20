package com.example.router.rest

import com.example.db.table.Course
import com.example.db.table.Score
import com.example.db.table.Student
import com.example.db.table.Teacher
import com.example.plugins.ok
import com.example.usecase.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.routing.*
import org.koin.ktor.ext.inject

fun Route.simpleInit() {
    val studentUseCase by inject<StudentUseCase>()
    val teacherUseCase by inject<TeacherUseCase>()
    val courseUseCase by inject<CourseUseCase>()
    val scoreUseCase by inject<ScoreUseCase>()

    route("/student") {
        put("/single") {
            val student = call.receive<Student>()
            studentUseCase.add(student)
        }
        put("/batch") {
            val studentList = call.receive<List<Student>>()
            studentUseCase.batchAdd(studentList)
        }
        get("/all") {
            call.ok(studentUseCase.findAll())
        }
    }
    route("/teacher") {
        put("/single") {
            val teacher = call.receive<Teacher>()
            teacherUseCase.add(teacher)
        }
        put("/batch") {
            val teacherList = call.receive<List<Teacher>>()
            teacherUseCase.batchAdd(teacherList)
        }
        get("/all") {
            call.ok(teacherUseCase.findAll())
        }
    }
    route("/course") {
        put("/single") {
            val course = call.receive<Course>()
            courseUseCase.add(course)
        }
        get("/all") {
            call.ok(courseUseCase.findAll())
        }
    }
    route("/score") {
        put("/single") {
            val course = call.receive<Score>()
            scoreUseCase.add(course)
        }
    }
}