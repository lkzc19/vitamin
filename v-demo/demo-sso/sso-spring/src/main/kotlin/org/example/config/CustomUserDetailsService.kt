package org.example.config

import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService

class CustomUserDetailsService : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        TODO("Not yet implemented")
    }
}