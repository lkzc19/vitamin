package org.example

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.concurrent.TimeUnit

@SpringBootApplication
class ShortlinkApplication

fun main(args: Array<String>) {
    runApplication<ShortlinkApplication>(*args)
}

val base62Map = mapOf(
    0u to '0', 1u to '1', 2u to '2', 3u to '3', 4u to '4',
    5u to '5', 6u to '6', 7u to '7', 8u to '8', 9u to '9',
    10u to 'a', 11u to 'b', 12u to 'c', 13u to 'd', 14u to 'e',
    15u to 'f', 16u to 'g', 17u to 'h', 18u to 'i', 19u to 'j',
    20u to 'k', 21u to 'l', 22u to 'm', 23u to 'n', 24u to 'o',
    25u to 'p', 26u to 'q', 27u to 'r', 28u to 's', 29u to 't',
    30u to 'u', 31u to 'v', 32u to 'w', 33u to 'x', 34u to 'y',
    35u to 'z', 36u to 'A', 37u to 'B', 38u to 'C', 39u to 'D',
    40u to 'E', 41u to 'F', 42u to 'G', 43u to 'H', 44u to 'I',
    45u to 'J', 46u to 'K', 47u to 'L', 48u to 'M', 49u to 'N',
    50u to 'O', 51u to 'P', 52u to 'Q', 53u to 'R', 54u to 'S',
    55u to 'T', 56u to 'U', 57u to 'V', 58u to 'W', 59u to 'X',
    60u to 'Y', 61u to 'Z'
)

object LinkMap {

    private val cache = Caffeine.newBuilder()
        .initialCapacity(10)
        .maximumSize(1000)
        .expireAfterWrite(30, TimeUnit.MINUTES)
        .build<String, String>()

    private val reverse = Caffeine.newBuilder()
        .initialCapacity(10)
        .maximumSize(1000)
        .expireAfterWrite(30, TimeUnit.MINUTES)
        .build<String, String>()

    operator fun get(k: String): String? = cache.getIfPresent(k)

    operator fun set(k: String, v: String) {
        cache.put(k, v)
        reverse.put(v, k)
    }

    // Reverse get
    fun ret(k: String): String? = reverse.getIfPresent(k)
}
