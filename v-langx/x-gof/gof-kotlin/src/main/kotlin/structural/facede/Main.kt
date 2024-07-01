package structural.facede


/**
 * 之后看下如
 * - [kotlin-logging](https://github.com/oshai/kotlin-logging)
 * - slf4j
 * 等门面日志是如何写的，
 * 如何通过引用不同的包就可以调用该包的api
 */
fun main() {
    val converter = VideoConversionFacade()
    val file = converter.convert("youtubevideo.ogg", "mp4")
    println(file)
}