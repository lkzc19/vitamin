package org.example.model.oauth2

import java.time.Instant


data class RegisteredClient(
    val id: String,
    val clientId: String,
    val clientIdIssuedAt: Instant,
    val clientSecret: String,
    val clientSecretExpiresAt: Instant,
    val clientName: String,
    val clientAuthenticationMethods: String,
    val authorizationGrantTypes: String,
    val redirectUris: String,
    val postLogoutRedirectUris: String,
    val scopes: String,
    val clientSettings: String,
    val tokenSettings: String,
)