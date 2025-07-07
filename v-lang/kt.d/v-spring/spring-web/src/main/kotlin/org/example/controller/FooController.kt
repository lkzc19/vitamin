package org.example.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.controller.param.FooParam
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class FooController {

    private val logger = KotlinLogging.logger {}

    @GetMapping("/foo.get")
    fun foo(@RequestParam name: String) = "foo...${name}"

    @PostMapping("/foo.post")
    fun foo(@RequestBody param: FooParam): String {
        logger.info { param }
        return "foo...${param.name}"
    }
}