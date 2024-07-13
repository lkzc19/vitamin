package org.example

import com.github.benmanes.caffeine.cache.Caffeine
import kotlin.test.Test

class CaffeineTests {

    @Test
    fun remove() {
        val cache = Caffeine.newBuilder()
            .maximumSize(2)
            .removalListener<String, String> { key, value, cause ->
                println("Key $key, Value $value was removed with cause $cause")
            }
            .build<String, String>()

        cache.put("1", "foo")
        cache.put("2", "bar")
        println(cache.getIfPresent("1"))
        println(cache.getIfPresent("1"))
        println(cache.getIfPresent("1"))
        println(cache.getIfPresent("2"))
        cache.put("3", "hello")
        cache.put("4", "world")
        println(" === ")
        cache.asMap().forEach { (key, value) -> println("$key -> $value") }
    }
}