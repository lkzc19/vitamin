package org.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CollectorServerApplication

fun main(args: Array<String>) {
    runApplication<CollectorServerApplication>(*args)
}
