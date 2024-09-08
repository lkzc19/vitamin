package org.example.model

import jakarta.persistence.*
import org.example.anno.NoArg
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.Instant
import java.time.LocalDateTime

@NoArg
@Entity
@Table(name = "v_user")
//@SQLDelete(sql = "UPDATE v_user SET updated_at = CURRENT_TIMESTAMP WHERE id = ?")
//@SQLRestriction("deleted_at IS NULL")
data class User(

    override val id: Long? = null,
    override val createdAt: Instant? = null,
    override val updatedAt: Instant? = null,
    override val deletedAt: Instant? = null,

    @Column
    val username: String,

    @Column
    val password: String,

    @Enumerated(EnumType.STRING)
    val role: UserRole = UserRole.User,

) : BaseModel(id, createdAt, updatedAt, deletedAt)

enum class UserRole {
    Root, Admin, User
}
