package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import java.util.Properties;

public class ProducerTest {

    @Test
    public void test() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = new KafkaProducer<>(props);

        String value = "{\"project\":\"ebank_mobile\",\"time\":1743042703317,\"type\":\"track\",\"distinct_id\":\"foo1\",\"anonymous_id\":\"miku1\",\"event\":\"ebank_click\",\"login_id\":\"foo1\",\"properties\":{\"pagecode\":\"public_93_02_xxx\",\"pagename\":\"投资生态圈机构首页——精选推荐——更多产品\",\"name\":\"投资生态圈机构首页——优选产品——更多产品\"}}";

//        for (int i = 0; i < 1000_0000; i++) {
//            producer.send(new ProducerRecord<>("stress_test_topic", value));
//        }

        producer.send(new ProducerRecord<>("stress_test_topic", value));

        producer.close();
    }
}