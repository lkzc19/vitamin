package org.example

import io.github.cdimascio.dotenv.dotenv

fun main() {
    println("Hello World!")

    val environment = dotenv {
        // 配置项
        filename = ".env" // 指定 .env 文件的名称
        ignoreIfMissing = true // 如果文件不存在，是否忽略
    }

    val appid = environment["WX_APPID"]
    val secret = environment["WX_SECRET"]

    println(appid)
    println(secret)
}