package org.example.rc

import com.fasterxml.jackson.databind.DeserializationFeature
import io.github.oshai.kotlinlogging.KotlinLogging
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.network.sockets.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.utils.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.utils.io.*
import org.example.rc.exception.RcException
import java.io.IOException

class RcClient {
    internal val logger = KotlinLogging.logger {}
    internal val client = HttpClient(CIO) {
        defaultRequest {
            url("http://192.168.0.134:4000")
        }
        install(ContentNegotiation) {
            jackson {
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            }
        }
        install(Logging) {
            level = LogLevel.NONE
            logger = Logger.DEFAULT
        }
        install(HttpRequestRetry) {
            retryIf { _, response ->
                val retry = response.status == HttpStatusCode.Unauthorized
                logger.info { "[rc.api]-[retryIf] 响应重试判断 [${response.status}]" }
                retry
            }
            // 只能捕获HTTP本身的异常 可以根据返回的数据在上面的方法做重试判断
            retryOnExceptionIf { request, cause ->
                val why: String
                val retry = when {
                    // 所以自定义异常在这里是捕获不到的
                    cause is RcException -> {
                        why = "RcException"
                        false
                    }
                    cause.isTimeoutException() -> {
                        why = "timeout"
                        false
                    }
                    cause is CancellationException -> {
                        why = "cancellation"
                        false
                    }
                    cause is IOException -> {
                        why = "服务 ${request.host} 未开启"
                        false
                    }
                    else -> {
                        why = "else"
                        true
                    }
                }
                logger.info { "[rc.api]-[retryOnExceptionIf] 异常响应重试判断 [$why]" }
                retry
            }
            delayMillis { retry ->
                logger.info { "[rc.api]-[delayMillis] 重试第 [$retry] 次" }
                retry * 3000L
            } // will retry in 3, 6, 9, etc. seconds
            modifyRequest { it.headers.append("X-RETRY-COUNT", retryCount.toString()) }
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 5000
            connectTimeoutMillis = 5000
            socketTimeoutMillis = 5000
        }
    }

    var authToken: String? = null
    var userId: String? = null

    // copy from HttpRequestRetry
    private fun Throwable.isTimeoutException(): Boolean {
        val exception = unwrapCancellationException()
        return exception is HttpRequestTimeoutException ||
                exception is ConnectTimeoutException ||
                exception is SocketTimeoutException
    }
}