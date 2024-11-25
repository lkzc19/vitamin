package org.example.repo

import org.example.model.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepo : JpaRepository<Book, Long> {
}