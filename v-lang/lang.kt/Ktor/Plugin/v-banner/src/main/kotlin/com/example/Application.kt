package com.example

import KBanner
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureRouting()
//    install(BannerPlugin) {
//        location = "fo/banner.txt"
//    }
    install(KBanner) {
        location = "fo/banner.txt"
    }
}
