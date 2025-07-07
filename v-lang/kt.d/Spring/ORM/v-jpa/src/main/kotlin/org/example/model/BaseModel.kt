package org.example.model

import jakarta.persistence.*
import java.time.Instant

@MappedSuperclass
abstract class BaseModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Instant? = null

    @Column(name = "updated_at")
    var updatedAt: Instant? = null

    /**
     * 插入前回调
     */
    @PrePersist
    fun prePersist() {
        this.createdAt = Instant.now()
    }

    /**
     * 插入后回调
     */
    @PostPersist
    fun postPersist() {
        println("postPersist...")
    }

    /**
     * 删除前回调
     */
    @PreRemove
    fun preRemove() {
        println("preRemove...")
    }

    /**
     * 删除后回调
     */
    @PostRemove
    fun postRemove() {
        println("postRemove...")
    }

    /**
     * 更新前回调
     */
    @PreUpdate
    fun preUpdate() {
        this.updatedAt = Instant.now()
    }

    /**
     * 更新后回调
     */
    @PostUpdate
    fun postUpdate() {
        println("postUpdate...")
    }

    /**
     * 查询后回调
     */
    @PostLoad
    fun postLoad() {
        println("postLoad...")
    }
}