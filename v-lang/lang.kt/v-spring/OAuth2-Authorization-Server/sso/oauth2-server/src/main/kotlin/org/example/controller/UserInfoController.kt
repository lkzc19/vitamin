package org.example.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


/**
 * @author Steve Riesenberg
 * @since 1.1
 */
@RestController
class UserInfoController {
    @GetMapping("/user_info")
    fun login(): String {
        return "login"
    }
}
