package org.example.controller

import org.example.utils.CodeUtils
import org.example.utils.LinkCache
import org.example.utils.QRCodeUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class SlController {

    @Value("\${vitamin.prefix}")
    lateinit var prefix: String

    @GetMapping("/")
    fun generate(@RequestParam link: String): ResponseEntity<Map<String, String>> {
        if (!link.startsWith("http://") && !link.startsWith("https://")) {
            return ResponseEntity.badRequest().body(mapOf("message" to "请输入 http:// 或 https:// 开头的网址"))
        }

        // 先判断是否存在映射
        val code = LinkCache.ret(link) ?: run {
            val code = CodeUtils.str2Base62(link)
            LinkCache[code] = link
            return@run code
        }

        // 生成短链二维码
        val shortURL = "$prefix$code"
        val qrcode = QRCodeUtils.genQRCode(shortURL)

        return mapOf(
            "origURL" to link,
            "shortURL" to shortURL,
            "QRCode" to qrcode
        ).let { ResponseEntity.ok(it) }
    }
}