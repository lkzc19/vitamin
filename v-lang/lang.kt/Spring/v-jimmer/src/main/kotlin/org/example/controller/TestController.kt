package org.example.controller

import org.babyfish.jimmer.sql.JSqlClient
import org.babyfish.jimmer.sql.exception.SaveException
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.example.model.Book
import org.example.repo.BookRepo
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController(private val bookRepo: BookRepo) {

    @PutMapping("/book")
    @Throws(SaveException::class) // (4)
    fun saveBook(@RequestBody input: BookInput): Book = // (5)
        bookRepo.save(input).modifiedEntity
}