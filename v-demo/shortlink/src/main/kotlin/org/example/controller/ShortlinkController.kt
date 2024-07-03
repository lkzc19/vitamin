package org.example.controller

import com.goncalossilva.murmurhash.MurmurHash3
import org.example.base62Map
import org.example.linkMap
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
        var hash = hashUtils.hash32x86(link.encodeToByteArray())
        val sb = StringBuilder()
        while (hash != 0u) {
            val remainder = hash % 62u
            hash /= 62u
            sb.append("${base62Map[remainder]}")
        }
        val code = sb.reversed().toString()
        linkMap[code] = link
        return "http://127.0.0.1:3000/" + sb.reversed().toString()
    }

    @GetMapping("/{code}")
    fun redirect302(@PathVariable code: String): ResponseEntity<Void> {
        val url = linkMap[code]

        return ResponseEntity.status(HttpStatus.FOUND)
            .header("Location", url)
            .build()
    }

    @GetMapping("/nahida")
    fun nahida(): String {
        return "nahida"
    }
}