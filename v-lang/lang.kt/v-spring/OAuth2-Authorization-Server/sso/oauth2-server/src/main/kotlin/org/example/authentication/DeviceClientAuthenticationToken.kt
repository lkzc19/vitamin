package org.example.authentication

import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient


class DeviceClientAuthenticationToken : OAuth2ClientAuthenticationToken {
    constructor(
        clientId: String?,
        clientAuthenticationMethod: ClientAuthenticationMethod?,
        credentials: Any?,
        additionalParameters: Map<String?, Any?>?
    ) : super(clientId, clientAuthenticationMethod, credentials, additionalParameters)

    constructor(
        registeredClient: RegisteredClient?,
        clientAuthenticationMethod: ClientAuthenticationMethod?,
        credentials: Any?
    ) : super(registeredClient, clientAuthenticationMethod, credentials)
}