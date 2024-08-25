package org.example.web.authentication

import jakarta.servlet.http.HttpServletRequest
import org.example.authentication.DeviceClientAuthenticationToken
import org.springframework.http.HttpMethod
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.OAuth2ErrorCodes
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.security.oauth2.server.authorization.authentication.OAuth2ClientAuthenticationToken
import org.springframework.security.web.authentication.AuthenticationConverter
import org.springframework.security.web.util.matcher.AndRequestMatcher
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.RequestMatcher
import org.springframework.util.StringUtils


class DeviceClientAuthenticationConverter(deviceAuthorizationEndpointUri: String) : AuthenticationConverter {
    private val deviceAuthorizationRequestMatcher: RequestMatcher
    private val deviceAccessTokenRequestMatcher: RequestMatcher

    init {
        val clientIdParameterMatcher =
            RequestMatcher { request: HttpServletRequest ->
                request.getParameter(
                    OAuth2ParameterNames.CLIENT_ID
                ) != null
            }
        this.deviceAuthorizationRequestMatcher = AndRequestMatcher(
            AntPathRequestMatcher(
                deviceAuthorizationEndpointUri, HttpMethod.POST.name()
            ),
            clientIdParameterMatcher
        )
        this.deviceAccessTokenRequestMatcher =
            RequestMatcher { request: HttpServletRequest ->
                AuthorizationGrantType.DEVICE_CODE.value == request.getParameter(OAuth2ParameterNames.GRANT_TYPE) && request.getParameter(
                    OAuth2ParameterNames.DEVICE_CODE
                ) != null && request.getParameter(OAuth2ParameterNames.CLIENT_ID) != null
            }
    }

    override fun convert(request: HttpServletRequest): Authentication? {
        if (!deviceAuthorizationRequestMatcher.matches(request) &&
            !deviceAccessTokenRequestMatcher.matches(request)
        ) {
            return null
        }

        // client_id (REQUIRED)
        val clientId = request.getParameter(OAuth2ParameterNames.CLIENT_ID)
        if (!StringUtils.hasText(clientId) ||
            request.getParameterValues(OAuth2ParameterNames.CLIENT_ID).size != 1
        ) {
            throw OAuth2AuthenticationException(OAuth2ErrorCodes.INVALID_REQUEST)
        }

        return DeviceClientAuthenticationToken(clientId, ClientAuthenticationMethod.NONE, null, null)
    }
}