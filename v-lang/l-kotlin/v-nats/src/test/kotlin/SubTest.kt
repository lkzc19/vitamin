import io.nats.client.*
import java.nio.charset.StandardCharsets
import kotlin.test.Test

class SubTest {
    @Test
    fun test1() {
        val o = Options.Builder()
            .server("nats://0.0.0.0:3224")
            .maxReconnects(-1)
            .build()
        val nc = Nats.connect(o)

        val d: Dispatcher = nc.createDispatcher { msg: Message? -> }

        val s: Subscription = d.subscribe("vitamin.nahida") { msg ->
            val response = String(msg.data, StandardCharsets.UTF_8)
            println("Message received (up to 100 times): $response")
        }
        d.unsubscribe(s, 100)
        Thread.sleep(5000)
    }
}