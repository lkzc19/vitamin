package org.example.repo

import jakarta.annotation.Resource
import org.example.model.Book
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.time.Instant

@SpringBootTest
class BookRepoTests {

    @Resource
    private lateinit var repo: BookRepo

    @Test
    fun test() {
        val book = Book(title = "论演员的自我修养（转）", author = "周")
//        book.createdAt = Instant.now()
        println(book)
        repo.save(book)
    }
}