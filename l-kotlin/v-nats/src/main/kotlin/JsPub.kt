import io.nats.client.Nats
import io.nats.client.Options
import io.nats.client.api.StreamConfiguration
import io.nats.client.impl.NatsMessage
import java.nio.charset.StandardCharsets


fun main() {
    val o = Options.Builder()
        .server("nats://0.0.0.0:3224")
        .maxReconnects(-1)
        .build()
    val nc = Nats.connect(o)

    val jsm = nc.jetStreamManagement()

    if (!jsm.streamNames.contains("vitamin")) {
        val sc = StreamConfiguration.builder()
            .name("vitamin")
            .description("this is vitamin stream")
            .subjects("vitamin.nahida", "vitamin.hutao")
            .maxConsumers(1)
            .build()
        println(jsm.addStream(sc))
    } else {
        println(jsm.getStreamInfo("vitamin"))
    }

    val js = nc.jetStream()
    val msg = NatsMessage.builder()
        .subject("vitamin.nahida")
        .data("hello", StandardCharsets.UTF_8)
        .build()

    val ack = js.publish(msg)
    println(ack)
    nc.close()
}
