package org.example.controller

import com.goncalossilva.murmurhash.MurmurHash3
import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import org.example.LinkMap
import org.example.base62Map
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.io.ByteArrayOutputStream
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


@RestController
class SlController {

    val hashUtils = MurmurHash3()
    @Value("\${vitamin.prefix}")
    lateinit var prefix: String

    @OptIn(ExperimentalEncodingApi::class)
    @GetMapping("/")
    fun generate(@RequestParam link: String): ResponseEntity<Map<String, String>> {
        if (!link.startsWith("http://") && !link.startsWith("https://")) {
            return ResponseEntity.badRequest().body(mapOf("message" to "请输入 http:// 或 https:// 开头的网址"))
        }

        // 先判断是否存在映射
        val code = LinkMap.ret(link) ?: run {
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
            return@run code
        }

        // 生成短链二维码
        val shortURL = "$prefix$code"
        val qrCodeWriter = QRCodeWriter()
        val bitMatrix = qrCodeWriter.encode(shortURL, BarcodeFormat.QR_CODE, 100, 100)
        val qrcode = ByteArrayOutputStream().use {
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", it)
            // 返回Base64编码的PNG数据
            return@use Base64.encode(it.toByteArray())
        }

        return mapOf(
            "origURL" to link,
            "shortURL" to shortURL,
            "QRCode" to qrcode
        ).let { ResponseEntity.ok(it) }
    }

    @GetMapping("/{code}")
    fun redirect302(@PathVariable code: String): ResponseEntity<Void> {
        // TODO 统计
        return LinkMap[code]?.let {
            ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", it)
                .build()
        }?: ResponseEntity.notFound().build()
    }

    @GetMapping("/ping")
    fun ping(): String {
        return "pong...$prefix"
    }
}