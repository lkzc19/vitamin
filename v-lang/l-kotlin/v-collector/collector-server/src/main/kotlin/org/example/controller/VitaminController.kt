package org.example.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.example.utils.GzipUtils
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class VitaminController {

    private val logger = KotlinLogging.logger {}

    @PostMapping
    fun vitamin(@RequestParam map: Map<String, Any>): String {
        logger.info { "原始数据: $map" }
        val decompress = GzipUtils.decompress(map["data_list"].toString())
        logger.info { "解压后: $decompress" }
        return "Request Logged"
    }
}