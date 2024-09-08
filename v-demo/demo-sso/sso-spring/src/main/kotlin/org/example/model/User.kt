package org.example.model

import jakarta.persistence.*
import org.example.anno.NoArg
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.Instant

@NoArg
@Entity
@Table(name = "v_user")
@SQLDelete(sql = "UPDATE v_user SET updated_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long?,

    @Column
    @CreatedDate
    override val createdAt: Instant?,

    @Column
    @LastModifiedDate
    override val updatedAt: Instant?,

    @Column
    override val deletedAt: Instant?,

    @Column
    val username: String,

    @Column
    val email: String,

    @Column
    val password: String,

    @Column
    val role: UserRole = UserRole.User,

    @Column
    val nickname: String?,

    @Column
    val status: UserStatus = UserStatus.Normal,

    ) : BaseModel(id, createdAt, updatedAt, deletedAt)

enum class UserRole {
    Root, Admin, User
}

enum class UserStatus {
    Normal, Ban
}