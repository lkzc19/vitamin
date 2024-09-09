package org.example.client.wx.rest

import okhttp3.Request
import org.example.client.wx.Client

fun Client.code2Session() {
    val request = Request.Builder()
        .url("$baseURL/sns/jscode2session")
        .build()
    client.newCall(request).execute().use {

    }
}