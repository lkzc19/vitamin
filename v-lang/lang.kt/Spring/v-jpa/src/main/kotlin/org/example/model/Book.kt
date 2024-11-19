package org.example.model

import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "e_book")
class Book(
    val title: String,
    val author: String,
) : BaseModel()