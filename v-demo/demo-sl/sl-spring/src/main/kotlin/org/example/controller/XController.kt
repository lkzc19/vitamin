package org.example.controller

import org.example.utils.LinkCache
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 在nginx处为此controller专门设置一个路由规则
 */
@RestController
@RequestMapping("/x")
class XController {

    @GetMapping("/{code}")
    fun redirect302(@PathVariable code: String): ResponseEntity<Void> {
        // TODO 统计
        return LinkCache[code]?.let {
            ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", it)
                .build()
        }?: ResponseEntity.notFound().build()
    }
}