package com.example.client

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.network.sockets.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.utils.*
import io.ktor.http.*
import io.ktor.util.*
import io.ktor.utils.io.*
import java.io.IOException


class VitaminClient {
    internal val logger = KotlinLogging.logger {}

    internal val client = HttpClient(CIO) {
        defaultRequest {
            url("http://localhost:3000")
            headers.appendIfNameAbsent("X-Foo", "foo")
            headers.appendIfNameAbsent("X-Bar", "bar")
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
//            filter { request ->
//                request.url.host.contains("ktor.io")
//            }
            sanitizeHeader { header ->
                header == "X-Foo" || header == "X-Drink-Ice"
            }
        }
        install(HttpRequestRetry) {
            // 默认最大重试 3 次
//            maxRetries = 3
            retryIf { _, response ->
                logger.info { "响应重试判断" }
                response.status == HttpStatusCode.Unauthorized
            }
            retryOnExceptionIf { request, cause ->
                when {
                    cause.isTimeoutException() -> false
                    cause is CancellationException -> false
                    cause is IOException -> {
                        logger.error { "服务 ${request.host} 未开启" }
                        false
                    }
                    else -> true
                }
            }
            delayMillis { retry ->
                logger.info { "重试 $retry 次" }
                retry * 3000L
            } // will retry in 3, 6, 9, etc. seconds
            modifyRequest { it.headers.append("X-RETRY-COUNT", retryCount. toString()) }
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 5000
            connectTimeoutMillis = 5000
            socketTimeoutMillis = 5000
        }
    }

    // copy from HttpRequestRetry
    private fun Throwable.isTimeoutException(): Boolean {
        val exception = unwrapCancellationException()
        return exception is HttpRequestTimeoutException ||
                exception is ConnectTimeoutException ||
                exception is SocketTimeoutException
    }
}

suspend fun VitaminClient.ping() {
    val response = client.get("/ping")
    logger.info { response.status }
}

suspend fun VitaminClient.log() {
    val response = client.get("/log")
    logger.info { response.status }
}

suspend fun VitaminClient.`401`() {
    val response = client.get("/401")
    logger.info { response.status }
}

suspend fun VitaminClient.retry401() {
    val response = client.get("/retry401")
    logger.info { response.status }
}

suspend fun VitaminClient.timeout() {
    val response = client.get("/sleep10s")
    logger.info { response.status }
}

suspend fun VitaminClient.fake() {
    // 本来随便写的域名 没想到会被转发||-_-
    val response = client.get("https://vitamin.xyz/")
    logger.info { response }
}

suspend fun VitaminClient.fakeIP() {
    val response = client.get("http://127.0.0.1:3333/")
    logger.info { response }
}