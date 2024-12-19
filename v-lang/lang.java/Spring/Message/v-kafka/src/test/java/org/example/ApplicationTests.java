package org.example;

import jakarta.annotation.Resource;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootTest
class ApplicationTests {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    void contextLoads() {
        kafkaTemplate.send("vitamin", "hello");
    }
}
