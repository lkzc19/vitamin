package org.example.dao

import org.example.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserDao : JpaRepository<User, Long>