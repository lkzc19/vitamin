package org.example.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class FooController {

    @GetMapping("/foo")
    fun foo(): String = "Hello World"
}