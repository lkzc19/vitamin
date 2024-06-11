package org.example.model

data class SignHeader (
    val sign: String,
    val timestamp: Long,
    val nonce: String,
)