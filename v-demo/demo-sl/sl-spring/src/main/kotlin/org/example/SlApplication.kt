package org.example

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.concurrent.TimeUnit

@SpringBootApplication
class SlApplication

fun main(args: Array<String>) {
    runApplication<SlApplication>(*args)
}
