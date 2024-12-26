package com.example

import io.ktor.server.application.*
import java.io.BufferedReader
import java.io.InputStreamReader

class BannerPluginConfig {
    var location: String = "banner.txt"
}

val BannerPlugin = createApplicationPlugin(
    name = "BannerPlugin",
    createConfiguration = ::BannerPluginConfig
) {
    var banner = """
        '##:::'##:'########::'#######::'########::
         ##::'##::... ##..::'##.... ##: ##.... ##:
         ##:'##:::::: ##:::: ##:::: ##: ##:::: ##:
         #####::::::: ##:::: ##:::: ##: ########::
         ##. ##:::::: ##:::: ##:::: ##: ##.. ##:::
         ##:. ##::::: ##:::: ##:::: ##: ##::. ##::
         ##::. ##:::: ##::::. #######:: ##:::. ##:
        ..::::..:::::..::::::.......:::..:::::..::
    """.trimIndent()

    val sb = StringBuilder()
    val location = pluginConfig.location
    object {}.javaClass.getResourceAsStream("/${location}")?.let { inputStream ->
        BufferedReader(InputStreamReader(inputStream)).use { reader ->
            reader.lines().forEach {
                sb.append(it + "\n")
            }
        }
        banner = sb.toString()
    }

    println("\n${banner}\n")
}