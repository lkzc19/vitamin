package org.example.client.wx.rest

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.example.client.wx.WxClient
import org.example.client.wx.response.Code2SessionResponse
import org.example.utils.JacksonUtils

suspend fun WxClient.code2Session(
    jsCode: String,
    grantType: String,
): Code2SessionResponse {
    val response = client.get("/sns/jscode2session") {
        contentType(ContentType.Application.Json)
        parameter("appid", appid)
        parameter("secret", secret)
        parameter("js_code", jsCode)
        parameter("grant_type", grantType)
    }

    if (!response.status.isSuccess()) {
        logger.error { response.status.description }
    }

    val body = JacksonUtils.json2Object(response.bodyAsText(), Code2SessionResponse::class.java)
    return body
}

suspend fun WxClient.code2Session1(
    jsCode: String,
    grantType: String,
) {
    val response = client.get("/sns/jscode2session") {
        contentType(ContentType.Application.Json)
        parameter("appid", appid)
        parameter("secret", secret)
        parameter("js_code", jsCode)
        parameter("grant_type", grantType)
    }

    if (!response.status.isSuccess()) {
        logger.error { response.status.description }
    }

    val body = response.bodyAsText()
    println(body)
}