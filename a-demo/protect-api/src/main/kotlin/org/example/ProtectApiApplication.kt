package org.example

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ProtectApiApplication

fun main(args: Array<String>) {
	runApplication<ProtectApiApplication>(*args)
}
