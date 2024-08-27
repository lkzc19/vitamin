package org.example.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class PingController {

    @RequestMapping("/ping")
    fun ping(): ResponseEntity<String> = ResponseEntity.ok("pong...")
}