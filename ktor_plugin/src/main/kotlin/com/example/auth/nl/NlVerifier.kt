package com.example.auth.nl

import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.interfaces.DecodedJWT

/**
 * Used to verify the JWT for its signature and claims. Implementations must be thread-safe. Instances are created
 * using [Verification].
 *
 * <pre>
 * try {
 * JWTVerifier verifier = JWTVerifier.init(Algorithm.RSA256(publicKey, privateKey)
 * .withIssuer("auth0")
 * .build();
 * DecodedJWT jwt = verifier.verify("token");
 * } catch (JWTVerificationException e) {
 * // invalid signature or claims
 * }
</pre> *
 */
interface NlVerifier {
  /**
   * Performs the verification against the given Token.
   *
   * @param token to verify.
   * @return a verified and decoded JWT.
   * @throws NlVerificationException if any of the verification steps fail
   */
  @Throws(NlVerificationException::class)
  fun verify(token: String?): DecodedJWT?
  
  /**
   * Performs the verification against the given [DecodedJWT].
   *
   * @param jwt to verify.
   * @return a verified and decoded JWT.
   * @throws JWTVerificationException if any of the verification steps fail
   */
  @Throws(NlVerificationException::class)
  fun verify(jwt: DecodedJWT?): DecodedJWT?
}
