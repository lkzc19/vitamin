package org.example.config


import com.nimbusds.jose.jwk.JWKSelector
import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.proc.SecurityContext
import org.example.authentication.DeviceClientAuthenticationProvider
import org.example.federation.FederatedIdentityIdTokenCustomizer
import org.example.jose.JwkUtils
import org.example.web.authentication.DeviceClientAuthenticationConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.http.MediaType
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod
import org.springframework.security.oauth2.core.oidc.OidcScopes
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationConsentService
import org.springframework.security.oauth2.server.authorization.JdbcOAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsentService
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher
import java.util.*


@Configuration(proxyBeanMethods = false)
class AuthorizationServerConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    fun authorizationServerSecurityFilterChain(
        http: HttpSecurity,
        registeredClientRepository: RegisteredClientRepository,
        authorizationServerSettings: AuthorizationServerSettings
    ): SecurityFilterChain {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);

        val deviceClientAuthenticationConverter = DeviceClientAuthenticationConverter(authorizationServerSettings.deviceAuthorizationEndpoint)
        val deviceClientAuthenticationProvider = DeviceClientAuthenticationProvider(registeredClientRepository)

        http
            .getConfigurer(OAuth2AuthorizationServerConfigurer::class.java)
            .deviceAuthorizationEndpoint {
                it.verificationUri("/activate")
            }
            .deviceVerificationEndpoint {
                it.consentPage(CUSTOM_CONSENT_PAGE_URI)
            }
            .clientAuthentication {
                it
                    .authenticationConverter(deviceClientAuthenticationConverter)
                    .authenticationProvider(deviceClientAuthenticationProvider)
            }
            .authorizationEndpoint {
                it.consentPage(CUSTOM_CONSENT_PAGE_URI)
            }

        http
            .exceptionHandling {
                it.defaultAuthenticationEntryPointFor(
                    LoginUrlAuthenticationEntryPoint("/login"),
                    MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                )
            }.oauth2ResourceServer {
                it.jwt(Customizer.withDefaults())
            }

        return http.build()
    }

    // 这个就是客户端的获取方式了，授权服务内部会调用做一些验证 例如 redirectUri
    // 官方给出的demo就先在内存里面初始化 也可以才有数据库的形式 实现 RegisteredClientRepository即可
    @Bean
    fun registeredClientRepository(jdbcTemplate: JdbcTemplate): RegisteredClientRepository {
        val repo = JdbcRegisteredClientRepository(jdbcTemplate)
        val registeredClientId = "vitamin-client"
        repo.findByClientId(registeredClientId) ?: run {
            val registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId(registeredClientId)
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc")
                .redirectUri("http://127.0.0.1:8080/authorized")
                .postLogoutRedirectUri("http://127.0.0.1:8080/logged-out")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope("vitamin.read")
                .scope("vitamin.write")
                .clientSettings(
                    ClientSettings.builder().requireAuthorizationConsent(true).build()
                ) //requireAuthorizationConsent(true) 授权页是有的 如果是false是没有的
                .build()
            repo.save(registeredClient)
        }
        return repo
    }

    //这个是oauth2的授权信息(包含了用户、token等其他信息) 这个也是可以扩展的 OAuth2AuthorizationService也是一个实现类
    @Bean
    fun authorizationService(
        jdbcTemplate: JdbcTemplate,
        registeredClientRepository: RegisteredClientRepository
    ): OAuth2AuthorizationService {
        return JdbcOAuth2AuthorizationService(jdbcTemplate, registeredClientRepository)
    }

    //这个是oauth2授权记录的持久化存储方式 看 JdbcOAuth2AuthorizationConsentService 就知道是基于数据库的了,当然也可以进行扩展 基于redis 后面再将 你可以看看 JdbcOAuth2AuthorizationConsentService的是一个实现
    @Bean
    fun authorizationConsentService(
        jdbcTemplate: JdbcTemplate,
        registeredClientRepository: RegisteredClientRepository
    ): OAuth2AuthorizationConsentService {
        // Will be used by the ConsentController
        return JdbcOAuth2AuthorizationConsentService(jdbcTemplate, registeredClientRepository)
    }

    @Bean
    fun idTokenCustomizer(): OAuth2TokenCustomizer<JwtEncodingContext> {
        return FederatedIdentityIdTokenCustomizer()
    }

    @Bean
    fun jwkSource(): JWKSource<SecurityContext> {
        val rsaKey = JwkUtils.generateRsa()
        val jwkSet = JWKSet(rsaKey)
        return JWKSource<SecurityContext> { jwkSelector: JWKSelector, _: SecurityContext ->
            jwkSelector.select(
                jwkSet
            )
        }
    }

    @Bean
    fun jwtDecoder(jwkSource: JWKSource<SecurityContext>): JwtDecoder {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource)
    }

    @Bean
    fun authorizationServerSettings(): AuthorizationServerSettings {
        return AuthorizationServerSettings.builder().build()
    }

    companion object {
        private const val CUSTOM_CONSENT_PAGE_URI: String = "/oauth2/consent" //这个是授权页
    }
}