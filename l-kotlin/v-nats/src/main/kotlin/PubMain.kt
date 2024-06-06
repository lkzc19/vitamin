
import io.nats.client.Connection
import io.nats.client.Nats
import io.nats.client.Options
import java.nio.charset.StandardCharsets


fun main() {
    println("---")

    val o = Options.Builder()
        .server("nats://0.0.0.0:3224")
        .maxReconnects(-1)
        .build()
    val nc: Connection = Nats.connect(o)

    nc.publish("vitamin.nahida", "hello world".toByteArray(StandardCharsets.UTF_8))

    Thread.sleep(5000)
    println("+++")
}