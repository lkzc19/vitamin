package org.example.client.wx

import okhttp3.OkHttpClient

object Client {
    internal val baseURL = "https://api.weixin.qq.com"
    internal val client = OkHttpClient()
}