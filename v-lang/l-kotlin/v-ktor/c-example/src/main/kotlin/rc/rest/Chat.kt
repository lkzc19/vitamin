package org.example.rc.rest

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.example.rc.RcClient
import org.example.rc.exception.RcException
import org.example.rc.request.PostMessageRequest
import org.example.rc.response.PostMessageResponse

/**
 * 发送消息 不需要创建房间
 * @param roomId username
 * @param text 显示文本
 * @see <a href="https://developer.rocket.chat/reference/api/rest-api/endpoints/core-endpoints/chat-endpoints/postmessage">postmessage</a>
 */
suspend fun RcClient.postMessage(
    roomId: String,
    text: String,
): PostMessageResponse {
    // 频道不加#号, 向用户发要加@
    val request = PostMessageRequest("@${roomId}", text)
    val response = client.post("/api/v1/chat.postMessage") {
        contentType(ContentType.Application.Json)
        header("X-Auth-Token", authToken)
        header("X-User-Id", userId)
        setBody(request)
    }

    val retry = (response.headers["X-RETRY-COUNT"] ?: "0").toInt()
    if (retry == 0) {
        throw RcException()
    }

    val body = response.body<PostMessageResponse>()
    if (!body.success) {
        logger.error { "[rc.api] - [login] - [${body.message}]" }
        throw RuntimeException("[rc.api] - [login] - [${body.message}]")
    }

    return body
}