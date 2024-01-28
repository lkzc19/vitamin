package com.example.auth.nl


open class NlVerificationException @JvmOverloads constructor(
  message: String?,
  cause: Throwable? = null
) : RuntimeException(message, cause)

