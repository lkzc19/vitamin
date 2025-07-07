package org.example.controller

import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames
import org.springframework.security.oauth2.core.oidc.OidcScopes
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.util.StringUtils
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import java.security.Principal

/**
 * @author Daniel Garnier-Moiroux
 */
@Controller
class AuthorizationConsentController(
    private val registeredClientRepository: RegisteredClientRepository,
    private val authorizationConsentService: OAuth2AuthorizationConsentService
) {
    @GetMapping(value = ["/oauth2/consent"])
    fun consent(
        principal: Principal, model: Model,
        @RequestParam(OAuth2ParameterNames.CLIENT_ID) clientId: String?,
        @RequestParam(OAuth2ParameterNames.SCOPE) scope: String?,
        @RequestParam(OAuth2ParameterNames.STATE) state: String?,
        @RequestParam(name = OAuth2ParameterNames.USER_CODE, required = false) userCode: String?
    ): String {
        // Remove scopes that were already approved

        val scopesToApprove: MutableSet<String> = HashSet()
        val previouslyApprovedScopes: MutableSet<String> = HashSet()
        val registeredClient = registeredClientRepository.findByClientId(clientId)
        val currentAuthorizationConsent =
            authorizationConsentService.findById(registeredClient!!.id, principal.name)
        val authorizedScopes = if (currentAuthorizationConsent != null) {
            currentAuthorizationConsent.scopes
        } else {
            emptySet()
        }
        for (requestedScope in StringUtils.delimitedListToStringArray(scope, " ")) {
            if (OidcScopes.OPENID == requestedScope) {
                continue
            }
            if (authorizedScopes.contains(requestedScope)) {
                previouslyApprovedScopes.add(requestedScope)
            } else {
                scopesToApprove.add(requestedScope)
            }
        }

        model.addAttribute("clientId", clientId)
        model.addAttribute("state", state)

        model.addAttribute("scopes", withDescription(scopesToApprove))
        model.addAttribute("previouslyApprovedScopes", withDescription(previouslyApprovedScopes))
        model.addAttribute("principalName", principal.name)
        model.addAttribute("userCode", userCode)
        if (StringUtils.hasText(userCode)) {
            model.addAttribute("requestURI", "/oauth2/device_verification")
        } else {
            model.addAttribute("requestURI", "/oauth2/authorize")
        }

        return "consent"
    }

    class ScopeWithDescription internal constructor(val scope: String) {
        val description: String

        init {
            this.description = scopeDescriptions.getOrDefault(scope, DEFAULT_DESCRIPTION)
        }

        companion object {
            private const val DEFAULT_DESCRIPTION =
                "UNKNOWN SCOPE - We cannot provide information about this permission, use caution when granting this."
            private val scopeDescriptions: MutableMap<String, String> = HashMap()

            init {
                scopeDescriptions[OidcScopes.PROFILE] =
                    "This application will be able to read your profile information."
                scopeDescriptions["vitamin.read"] = "This application will be able to read your message."
                scopeDescriptions["vitamin.write"] =
                    "This application will be able to add new messages. It will also be able to edit and delete existing messages."
                scopeDescriptions["other.scope"] = "This is another scope example of a scope description."
            }
        }
    }

    companion object {
        private fun withDescription(scopes: Set<String>): Set<ScopeWithDescription> {
            val scopeWithDescriptions: MutableSet<ScopeWithDescription> = HashSet()
            for (scope in scopes) {
                scopeWithDescriptions.add(ScopeWithDescription(scope))
            }
            return scopeWithDescriptions
        }
    }
}