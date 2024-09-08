package org.example.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.Instant;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "v_user")
public class User extends BaseModel {

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
