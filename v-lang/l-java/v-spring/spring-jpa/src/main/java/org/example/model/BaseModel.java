package org.example.model;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreatedDate
    public Instant createdAt;

    @Column(name = "updated_at")
    @LastModifiedDate
    public Instant updatedAt;

    @Column(name = "deleted_at")
    public Instant deletedAt;
}