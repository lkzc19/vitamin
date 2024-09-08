package org.example.dao

import org.example.model.User
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserDaoTests {

    @Autowired
    lateinit var userDao: UserDao

    @Test
    fun create() {
        val user = User(username = "admin", password = "admin")
        userDao.save(user)
    }

    @Test
    fun update() {
        val user = User(username = "admin", password = "admin")
        userDao.save(user)
    }
}