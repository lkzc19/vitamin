package org.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FsSpringApplication

fun main(args: Array<String>) {
    runApplication<FsSpringApplication>(*args)
}
