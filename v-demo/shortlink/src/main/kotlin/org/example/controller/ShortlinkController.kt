package org.example.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ShortlinkController {

    @GetMapping("/link")
    fun generate(@RequestParam link: String): String {
        TODO("not implemented")
    }

    @GetMapping("/{code}")
    fun redirect302(@PathVariable code: String): String {
        TODO("not implemented")
    }
}