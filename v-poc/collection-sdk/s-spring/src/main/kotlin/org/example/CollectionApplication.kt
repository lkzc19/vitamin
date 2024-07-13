package org.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CollectionApplication

fun main(args: Array<String>) {
    runApplication<CollectionApplication>(*args)
}
