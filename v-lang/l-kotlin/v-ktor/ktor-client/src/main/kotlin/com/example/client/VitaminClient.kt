package com.example.client

import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.http.*
import java.net.SocketException


class VitaminClient {
    internal val logger = KotlinLogging.logger {}
    internal val client = HttpClient(CIO) {
        defaultRequest {
            url("http://localhost:3000")
        }
        install(HttpRequestRetry) {
            maxRetries = 3
            retryIf { _, response -> response.status == HttpStatusCode.Unauthorized }
            // retryOnExceptionIf { request, cause -> cause is NetworkError }
            delayMillis { retry ->
                logger.info { "重试 $retry 次" }
                retry * 3000L
            } // will retry in 3, 6, 9, etc. seconds
            modifyRequest { it.headers.append("X-RETRY-COUNT", retryCount. toString()) }
        }
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

// TODO 超时

suspend fun VitaminClient.fake() {
    try {
        val response = client.get("https://vitamin.xyz/")
        logger.info { response }
    } catch (e: SocketException) {
        logger.error { "连接失败" }
    }
}
