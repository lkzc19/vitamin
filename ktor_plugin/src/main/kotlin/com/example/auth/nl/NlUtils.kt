package com.example.auth.nl

import io.ktor.server.auth.*

internal fun AuthenticationContext.bearerChallenge(
  cause: AuthenticationFailedCause,
  error: String,
  message: String,
  challengeFunction: NlAuthChallengeFunction
) {
  challenge(NlAuthKey, cause) { challenge, call ->
    challengeFunction(NlChallengeContext(call), error, message)
    if (!challenge.completed && call.response.status() != null) {
      challenge.complete()
    }
  }
}