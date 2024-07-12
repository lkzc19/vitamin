package org.example.controller

import com.goncalossilva.murmurhash.MurmurHash3
import org.example.base62Map
import org.example.linkMap
import org.example.salt
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ShortlinkController {

    val hashUtils = MurmurHash3()

    @GetMapping("/")
    fun generate(@RequestParam link: String): String {
        // 先判断是否存在映射
        linkMap[link]?.let {
            return "http://127.0.0.1:3000/$it"
        }

        var count = 0
        var str = link
        var code: String
        do { // hash碰撞后加一些字符串继续hash
            var hash = hashUtils.hash32x86(str.encodeToByteArray())
            val sb = StringBuilder()
            while (hash != 0u) {
                val remainder = hash % 62u
                hash /= 62u
                sb.append("${base62Map[remainder]}")
            }
            code = sb.reversed().toString()
            str += salt[count % salt.size]
            count++
        } while (linkMap[code] != null) // 此处可以使用布隆过滤器来判断hash是否冲突 来提高性能
        linkMap[code] = link
        return "http://127.0.0.1:3000/$code"
    }

    @GetMapping("/{code}")
    fun redirect302(@PathVariable code: String): ResponseEntity<Void> {
        val url = linkMap[code]

        return ResponseEntity.status(HttpStatus.FOUND)
            .header("Location", url)
            .build()
    }

    @GetMapping("/ping")
    fun ping(): String {
        return "pong..."
    }
}