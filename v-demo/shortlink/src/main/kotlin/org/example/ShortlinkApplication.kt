package org.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ShortlinkApplication

fun main(args: Array<String>) {
    runApplication<ShortlinkApplication>(*args)
}
