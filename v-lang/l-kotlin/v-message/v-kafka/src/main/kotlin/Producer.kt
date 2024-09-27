package org.example

import org.apache.kafka.clients.producer.KafkaProducer
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.serialization.StringSerializer
import java.util.*

fun main() {
    val props = Properties().apply {
        put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9094")
        put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
        put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)
    }

    val producer = KafkaProducer<String, String>(props)
    val record = ProducerRecord("vitamin", "key", "Hello, hutao")
    producer.send(record) { metadata, exception ->
        if (exception != null) {
            println("Error while producing message: ${exception.message}")
        } else {
            println("Sent message to partition ${metadata?.partition()} with offset ${metadata?.offset()}")
        }
    }
    producer.close()
}