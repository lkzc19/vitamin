package org.example.model


data class AuthorizationConsent(
    val registeredClientId: String,
    val principalName: String,
    val authorities: String,
)