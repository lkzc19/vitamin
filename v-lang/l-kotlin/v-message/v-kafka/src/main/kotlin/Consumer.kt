package org.example

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.apache.kafka.common.serialization.StringDeserializer
import java.time.Duration
import java.util.*

fun main() {
    val properties = Properties().apply {
        put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094")
        put(ConsumerConfig.GROUP_ID_CONFIG, "kotlin-consumer-group")
        put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
        put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
        put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
    }

    val consumer = KafkaConsumer<String, String>(properties)
    consumer.subscribe(listOf("vitamin"))

    consumer.use {
        while (true) {
            val records = it.poll(Duration.ofMillis(100))
            records.forEach { record ->
                println("Consumed record: key=${record.key()}, value=${record.value()}, partition=${record.partition()}, offset=${record.offset()}")
            }
        }
    }
}