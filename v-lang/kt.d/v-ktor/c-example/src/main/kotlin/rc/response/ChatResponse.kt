package org.example.rc.response

import com.fasterxml.jackson.annotation.JsonProperty

data class PostMessageResponse(
    val success: Boolean = false,
    val error: String?,
    val errorType: String?,
    val message: Message?,
)

data class Message(
    @JsonProperty("_id")
    val id: String,
)