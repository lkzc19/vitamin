package org.example.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@Controller
class LoginController {

    @GetMapping("/login")
    fun login(): String {
        return "login"
    }
}