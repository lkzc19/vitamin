package org.example.springweb

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringWebApplication

fun main(args: Array<String>) {
    runApplication<SpringWebApplication>(*args)
}
