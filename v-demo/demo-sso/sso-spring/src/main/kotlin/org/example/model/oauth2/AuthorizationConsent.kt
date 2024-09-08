package org.example.model.oauth2


data class AuthorizationConsent(
    val registeredClientId: String,
    val principalName: String,
    val authorities: String,
)