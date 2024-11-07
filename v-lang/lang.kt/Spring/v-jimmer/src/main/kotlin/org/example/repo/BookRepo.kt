package org.example.repo

import org.babyfish.jimmer.spring.repo.support.AbstractKotlinRepository
import org.babyfish.jimmer.sql.kt.KSqlClient
import org.example.model.Book
import org.springframework.stereotype.Repository

@Repository
class BookRepo(sql: KSqlClient) : AbstractKotlinRepository<Book, Long>(sql) {
//    fun saveBook(input: BookInput): Long =
//        sqlClient
//            .save(input)
//            .modifiedEntity
//            // 如果`input.id`为null，返回自动分配的id
//            .id
}