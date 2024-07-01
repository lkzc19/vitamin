package org.example

import java.math.BigInteger
import java.security.MessageDigest
import kotlin.test.Test

class Test {

    @Test
    fun hash() {
        val inputString = "Hello, World!"
        val hashCode = MessageDigest.getInstance("MD5")
            .digest(inputString.toByteArray())
            .let { BigInteger(1, it) }

        println("$inputString ===> $hashCode")

        val characters = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val base = characters.length
        val stack = mutableListOf<Char>()

        var quotient = hashCode
        while (quotient > BigInteger.ZERO) {
            val remainder = (quotient % base.toBigInteger()).toInt()
            stack.add(characters[remainder])
            quotient /= base.toBigInteger()
        }

        println(stack.reversed().joinToString(""))
    }
}