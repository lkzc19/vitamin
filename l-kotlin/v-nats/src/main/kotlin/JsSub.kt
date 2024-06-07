import io.nats.client.JetStreamSubscription
import io.nats.client.Nats
import io.nats.client.Options
import io.nats.client.api.ConsumerConfiguration
import java.time.Duration


fun main() {
    val o = Options.Builder()
        .server("nats://0.0.0.0:3224")
        .maxReconnects(-1)
        .build()
    val nc = Nats.connect(o)

    val pullSub = ConsumerConfiguration.builder()
        .name("vc")
        .buildPullSubscribeOptions("vitamin")

    val js = nc.jetStream()

    val sub = js.subscribe("vitamin.nahida", pullSub)

    while (true) {
        sub.pull(42)
        val m = sub.nextMessage(Duration.ofSeconds(1))
        if (m != null && m.isJetStream) {
            println(m)
            m.ack()
        }
    }
}
