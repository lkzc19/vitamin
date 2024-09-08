package org.example.config

import jakarta.annotation.Resource
import org.example.repo.UserRepo
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class CustomUserDetailsService : UserDetailsService {

    @Resource
    lateinit var userRepo: UserRepo

    override fun loadUserByUsername(username: String): UserDetails {
//        userRepo.findBy()

        TODO("Not yet implemented")
    }
}