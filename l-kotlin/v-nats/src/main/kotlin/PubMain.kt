
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

    nc.publish("Test", "hello world".toByteArray(StandardCharsets.UTF_8))

    Thread.sleep(5000)
    println("+++")
}