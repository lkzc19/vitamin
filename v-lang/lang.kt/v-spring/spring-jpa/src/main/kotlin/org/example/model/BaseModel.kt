package org.example.model

import jakarta.persistence.*
import org.example.anno.NoArg
import org.hibernate.annotations.Where
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.time.LocalDateTime

@NoArg
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open val id: Long?,

    @Column(name = "created_at")
    @CreatedDate
    open val createdAt: Instant?,

    @Column(name = "updated_at")
    @LastModifiedDate
    open val updatedAt: Instant?,

    @Column(name = "deleted_at")
    open val deletedAt: Instant?,
)