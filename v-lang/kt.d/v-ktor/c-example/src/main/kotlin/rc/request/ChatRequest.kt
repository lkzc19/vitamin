package org.example.rc.request

data class PostMessageRequest(
    val roomId: String,
    val text: String,
)

