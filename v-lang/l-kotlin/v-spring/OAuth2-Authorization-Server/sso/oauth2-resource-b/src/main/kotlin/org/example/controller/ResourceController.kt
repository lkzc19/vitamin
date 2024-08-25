package org.example.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class ResourceController {

    @GetMapping("/res1")
    fun getRes1(): ResponseEntity<String> = ResponseEntity.ok("服务A -> 资源1")

    @GetMapping("/res2")
    fun getRes2(): ResponseEntity<String> = ResponseEntity.ok("服务A -> 资源2")
}