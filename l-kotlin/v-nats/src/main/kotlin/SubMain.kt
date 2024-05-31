import io.nats.client.Connection
import io.nats.client.Nats
import io.nats.client.Options
import java.nio.charset.StandardCharsets

fun main() {
    println("---")

    val o: Options = Options.Builder()
        .server("nats://0.0.0.0:4222")
        .maxReconnects(-1)
        .build()
    val nc: Connection = Nats.connect(o)

    println("=====")
    println(nc.statistics)
    println("=====")
    println(nc.clientInetAddress)
    println("=====")
    println(nc.status)


//    val d = nc.createDispatcher {
//        val response = it.data.toString(StandardCharsets.UTF_8)
//        println(response)
//    }
//
//    d.subscribe("Test")

    Thread.sleep(5000)
    println("+++")
}