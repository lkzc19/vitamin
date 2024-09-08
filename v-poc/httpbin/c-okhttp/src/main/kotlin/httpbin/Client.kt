package org.example.httpbin

import okhttp3.OkHttpClient



object Client {
    val client: OkHttpClient = OkHttpClient().newBuilder()

        .build()
}