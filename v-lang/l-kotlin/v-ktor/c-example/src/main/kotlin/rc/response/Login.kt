package org.example.rc.response


data class LoginResponse(
    val status: String,
    val error: String?,
    val message: String?,
    val data: LoginData?
)

data class LoginData(
    val userId: String,
    val authToken: String
)