package org.example;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;


@Component
public class KafkaConsumer {

    @KafkaListener(topics = {"vitamin"})
    public void handleMessage(ConsumerRecord<String, String> record) {
        System.out.println(record.value());
    }
}
