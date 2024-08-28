package org.example.rc.rest

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import org.example.rc.RcClient
import org.example.rc.request.LoginRequest
import org.example.rc.response.LoginResponse
import org.example.rc.response.Status
import java.util.*

/**
 * 登录
 * @param username 用户名
 * @param password 密码
 * @see <a href="https://developer.rocket.chat/reference/api/rest-api/endpoints/other-important-endpoints/authentication-endpoints/login">login</a>
 */
suspend fun RcClient.login(
    username: String,
    password: String,
): LoginResponse {
    val request = LoginRequest(username, password)
    val response: LoginResponse = client.post("/api/v1/login") {
        contentType(ContentType.Application.Json)
        setBody(request)
    }.body()

    if (response.status != Status.SUCCESS.toString()) {
        logger.error { "[rc.api] - [login] - [${response.message}]" }
        throw RuntimeException(response.message)
    }

    return response
}