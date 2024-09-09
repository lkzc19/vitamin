package org.example.client.wx

import com.fasterxml.jackson.databind.DeserializationFeature
import io.github.cdimascio.dotenv.dotenv
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.jackson.*

class WxClient {
    internal val appid: String
    internal val secret: String
    init {
        val environment = dotenv {
            // 配置项
            filename = ".env" // 指定 .env 文件的名称
            ignoreIfMissing = true // 如果文件不存在，是否忽略
        }

        appid = environment["WX_APPID"]
        secret = environment["WX_SECRET"]
    }

    internal val logger = KotlinLogging.logger {}
    internal val client = HttpClient(CIO) {
        defaultRequest {
            url("https://api.weixin.qq.com")
        }
//        install(ContentNegotiation) {
//            jackson {
//                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
//            }
//        }
        install(Logging) {
            level = LogLevel.NONE
            logger = Logger.DEFAULT
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 5000
            connectTimeoutMillis = 5000
            socketTimeoutMillis = 5000
        }
    }
}

fun WxClient.info() {
    println("appid\t$appid")
    println("secret\t$secret")
}