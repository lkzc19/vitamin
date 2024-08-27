package org.example.authentication

import org.apache.commons.logging.Log
import org.apache.commons.logging.LogFactory
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.core.OAuth2ErrorCodes
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.util.Assert


class DeviceClientAuthenticationProvider(private var registeredClientRepository: RegisteredClientRepository?) : AuthenticationProvider{
    private val logger: Log = LogFactory.getLog(javaClass)

    init {
        Assert.notNull(registeredClientRepository, "registeredClientRepository cannot be null")
    }

    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication? {
        val deviceClientAuthentication: DeviceClientAuthenticationToken =
            authentication as DeviceClientAuthenticationToken

        if (ClientAuthenticationMethod.NONE != deviceClientAuthentication.clientAuthenticationMethod) {
            return null
        }

        val clientId: String = deviceClientAuthentication.principal.toString()
        val registeredClient = registeredClientRepository!!.findByClientId(clientId)
        if (registeredClient == null) {
            throwInvalidClient(OAuth2ParameterNames.CLIENT_ID)
        }

        if (logger.isTraceEnabled) {
            logger.trace("Retrieved registered client")
        }

        if (!registeredClient!!.clientAuthenticationMethods.contains(
                deviceClientAuthentication.clientAuthenticationMethod
            )
        ) {
            throwInvalidClient("authentication_method")
        }

        if (logger.isTraceEnabled) {
            logger.trace("Validated device client authentication parameters")
        }

        if (logger.isTraceEnabled) {
            logger.trace("Authenticated device client")
        }

        return DeviceClientAuthenticationToken(
            registeredClient,
            deviceClientAuthentication.clientAuthenticationMethod, null
        )
    }

    override fun supports(authentication: Class<*>): Boolean {
        return DeviceClientAuthenticationToken::class.java.isAssignableFrom(authentication)
    }

    private fun throwInvalidClient(parameterName: String) {
        val error = OAuth2Error(
            OAuth2ErrorCodes.INVALID_CLIENT,
            "Device client authentication failed: $parameterName",
            ERROR_URI
        )
        throw OAuth2AuthenticationException(error)
    }

    companion object {
        private const val ERROR_URI: String = "https://datatracker.ietf.org/doc/html/rfc6749#section-3.2.1"
    }
}