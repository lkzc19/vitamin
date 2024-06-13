package com.example.repository

import com.example.db.table.Course
import com.example.db.table.Score
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import kotlin.test.BeforeTest
import kotlin.test.Test

class ScoreRepositoryTest {

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
        ScoreRepository().create(Score(studentId = 2, courseId = 2, score = 20))
        ScoreRepository().create(Score(studentId = 2, courseId = 3, score = 95))
        ScoreRepository().create(Score(studentId = 2, courseId = 4, score = 63))
        ScoreRepository().create(Score(studentId = 2, courseId = 5, score = 62))
        ScoreRepository().create(Score(studentId = 2, courseId = 6, score = 67))
        ScoreRepository().create(Score(studentId = 3, courseId = 2, score = 24))
        ScoreRepository().create(Score(studentId = 3, courseId = 3, score = 30))
        ScoreRepository().create(Score(studentId = 3, courseId = 4, score = 50))
        ScoreRepository().create(Score(studentId = 3, courseId = 5, score = 94))
        ScoreRepository().create(Score(studentId = 3, courseId = 6, score = 87))
        ScoreRepository().create(Score(studentId = 4, courseId = 2, score = 95))
        ScoreRepository().create(Score(studentId = 4, courseId = 3, score = 98))
        ScoreRepository().create(Score(studentId = 4, courseId = 4, score = 48))
        ScoreRepository().create(Score(studentId = 4, courseId = 5, score = 48))
        ScoreRepository().create(Score(studentId = 4, courseId = 6, score = 87))
        ScoreRepository().create(Score(studentId = 5, courseId = 2, score = 65))
        ScoreRepository().create(Score(studentId = 5, courseId = 3, score = 84))
        ScoreRepository().create(Score(studentId = 5, courseId = 4, score = 84))
        ScoreRepository().create(Score(studentId = 5, courseId = 5, score = 34))
        ScoreRepository().create(Score(studentId = 5, courseId = 6, score = 37))
    }
}