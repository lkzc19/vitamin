package com.example.auth.nl


import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.auth.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory

internal val NlAuthKey: Any = "NlAuth"

internal val NlLogger: Logger = LoggerFactory.getLogger("io.ktor.auth.nl")

public class NlAuthenticationProvider internal constructor(config: Config) : AuthenticationProvider(config) {
  
  private val authHeader: (ApplicationCall) -> HttpAuthHeader? = config.authHeader
  private val verifier: ((HttpAuthHeader) -> NlVerifier?) = config.verifier
  private val authenticationFunction = config.authenticationFunction
  private val challengeFunction: NlAuthChallengeFunction = config.challenge
  
  override suspend fun onAuthenticate(context: AuthenticationContext) {
    val call = context.call
    val signature = authHeader(call)
    if (signature == null) {
      context.bearerChallenge(
        cause = AuthenticationFailedCause.NoCredentials,
        error = "SIGNATURE-NULL",
        message = "[signature]不可为空",
        challengeFunction
      )
      return
    }
    
    try {
      val principal = verifyAndValidate(call, verifier(token), token, schemes, authenticationFunction)
      if (principal != null) {
        context.principal(name, principal)
        return
      }
      
      context.bearerChallenge(
        AuthenticationFailedCause.InvalidCredentials,
        realm,
        schemes,
        challengeFunction
      )
    } catch (cause: Throwable) {
      val message = cause.message ?: cause.javaClass.simpleName
      JWTLogger.trace("JWT verification failed", cause)
      context.error(JWTAuthKey, AuthenticationFailedCause.Error(message))
    }
  }
  
  /**
   * A configuration for the [jwt] authentication provider.
   */
  public class Config internal constructor(name: String?) : AuthenticationProvider.Config(name) {
    internal var authenticationFunction: AuthenticationFunction<JWTCredential> = {
      throw NotImplementedError(
        "JWT auth validate function is not specified. Use jwt { validate { ... } } to fix."
      )
    }
    
    internal var schemes = JWTAuthSchemes("Bearer")
    
    internal var authHeader: (ApplicationCall) -> HttpAuthHeader? =
      { call -> call.request.parseAuthorizationHeaderOrNull() }
    
    internal var verifier: ((HttpAuthHeader) -> JWTVerifier?) = { null }
    
    internal var challenge: NlAuthChallengeFunction = { scheme, realm ->
      call.respond(
        UnauthorizedResponse(
          HttpAuthHeader.Parameterized(
            scheme,
            mapOf(HttpAuthHeader.Parameters.Realm to realm)
          )
        )
      )
    }
    
    /**
     * Specifies a JWT realm to be passed in `WWW-Authenticate` header.
     */
    public var realm: String = "Ktor Server"
    
    /**
     * Retrieves an HTTP authentication header.
     * By default, it parses the `Authorization` header content.
     */
    public fun authHeader(block: (ApplicationCall) -> HttpAuthHeader?) {
      authHeader = block
    }
    
    /**
     * @param [defaultScheme] default scheme used to challenge the client when no valid authentication is provided
     * @param [additionalSchemes] additional schemes that are accepted when validating the authentication
     */
    public fun authSchemes(defaultScheme: String = "Bearer", vararg additionalSchemes: String) {
      schemes = JWTAuthSchemes(defaultScheme, *additionalSchemes)
    }
    
    /**
     * Provides a [JWTVerifier] used to verify a token format and signature.
     * @param [verifier] verifies token format and signature
     */
    public fun verifier(verifier: JWTVerifier) {
      this.verifier = { verifier }
    }
    
    /**
     * Provides a [JWTVerifier] used to verify a token format and signature.
     */
    public fun verifier(verifier: (HttpAuthHeader) -> JWTVerifier?) {
      this.verifier = verifier
    }
    
    /**
     * Provides a [JWTVerifier] used to verify a token format and signature.
     * @param [jwkProvider] provides the JSON Web Key
     * @param [issuer] the issuer of the JSON Web Token
     * @param [configure] function is applied during [JWTVerifier] construction
     */
    public fun verifier(jwkProvider: JwkProvider, issuer: String, configure: JWTConfigureFunction = {}) {
      verifier = { token -> getVerifier(jwkProvider, issuer, token, schemes, configure) }
    }
    
    /**
     * Provides a [JWTVerifier] used to verify a token format and signature.
     * @param [jwkProvider] provides the JSON Web Key
     * @param [configure] function will be applied during [JWTVerifier] construction
     */
    public fun verifier(jwkProvider: JwkProvider, configure: JWTConfigureFunction = {}) {
      verifier = { token -> getVerifier(jwkProvider, token, schemes, configure) }
    }
    
    /**
     * Provides a [JWTVerifier] used to verify a token format and signature.
     *
     * @param [issuer] of the JSON Web Token
     * @param [audience] restriction
     * @param [algorithm] for validations of token signatures
     */
    public fun verifier(
      issuer: String,
      audience: String,
      algorithm: Algorithm,
      block: Verification.() -> Unit = {}
    ) {
      val verification: Verification = JWT
        .require(algorithm)
        .withAudience(audience)
        .withIssuer(issuer)
      
      verification.apply(block)
      verifier(verification.build())
    }
    
    /**
     * Provides a [JWTVerifier] used to verify a token format and signature.
     *
     * @param [issuer] the issuer of JSON Web Token
     * @param [block] configuration of [JwkProvider]
     */
    public fun verifier(issuer: String, block: JWTConfigureFunction = {}) {
      val provider = JwkProviderBuilder(issuer).build()
      verifier = { token -> getVerifier(provider, token, schemes, block) }
    }
    
    /**
     * Allows you to perform additional validations on the JWT payload.
     * @return a principal (usually an instance of [JWTPrincipal]) or `null`
     */
    public fun validate(validate: suspend ApplicationCall.(JWTCredential) -> Principal?) {
      authenticationFunction = validate
    }
    
    /**
     * Specifies what to send back if JWT authentication fails.
     */
    public fun challenge(block: JWTAuthChallengeFunction) {
      challenge = block
    }
    
    internal fun build() = JWTAuthenticationProvider(this)
  }
}


public fun AuthenticationConfig.jwt(
  name: String? = null,
  configure: NlAuthenticationProvider.Config.() -> Unit
) {
  val provider = NlAuthenticationProvider.Config(name).apply(configure).build()
  register(provider)
}

/**
 * 作为 [NlAuthChallengeFunction] 的上下文.
 */
public class NlChallengeContext(
  public val call: ApplicationCall
)

/**
 * 指定在 NL 身份验证失败时要发回的内容.
 */
public typealias NlAuthChallengeFunction =
    suspend NlChallengeContext.(error: String, message: String) -> Unit