package org.example.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BarController {

    @GetMapping("/bar")
    fun bar() = "bar..."

}