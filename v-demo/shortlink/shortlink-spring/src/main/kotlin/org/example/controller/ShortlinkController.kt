package org.example.controller

import com.goncalossilva.murmurhash.MurmurHash3
import org.example.LinkMap
import org.example.base62Map
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class ShortlinkController {

    val hashUtils = MurmurHash3()
    val prefix = "http://localhost:3000/"

    @GetMapping("/")
    fun generate(@RequestParam link: String): ResponseEntity<Map<String, String>> {
        // 先判断是否存在映射
        LinkMap.ret(link)?.let { code ->
            return mapOf(
                "origURL" to link,
                "shortURL" to "$prefix$code"
            ).let { ResponseEntity.ok(it) }
        }

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
            str += System.currentTimeMillis()
        } while (LinkMap[code] != null) // 此处可以使用布隆过滤器来判断hash是否冲突 来提高性能
        LinkMap[code] = link

        return mapOf(
            "origURL" to link,
            "shortURL" to "$prefix$code"
        ).let { ResponseEntity.ok(it) }
    }

    @GetMapping("/{code}")
    fun redirect302(@PathVariable code: String): ResponseEntity<Void> {
        return LinkMap[code]?.let {
            ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", it)
                .build()
        }?: ResponseEntity.notFound().build()
    }

    @GetMapping("/ping")
    fun ping(): String {
        return "pong..."
    }
}