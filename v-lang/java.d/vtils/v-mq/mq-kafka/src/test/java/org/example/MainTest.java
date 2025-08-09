package org.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class MainTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Test
    public void testAll() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);

        BufferedReader reader = new BufferedReader(new FileReader("/Users/lkzc19/Projects/startorch/g_chaumet/kafka_event/event.log"));

        String line;
        while ((line = reader.readLine()) != null) {
            ProducerRecord<String, String> record = new ProducerRecord<>("event_topic", null, line);
            producer.send(record);
            TimeUnit.SECONDS.sleep(1);
        }

        reader.close();
        producer.close();
    }

    @SneakyThrows
    @Test
    public void testGetTotalAmount() {
        BufferedReader reader = new BufferedReader(new FileReader("/Users/lkzc19/Projects/startorch/g_chaumet/kafka_event/event.log"));

        int count = 0;

        String line;
        while ((line = reader.readLine()) != null) {
            JsonNode jsonNode = objectMapper.readTree(line);
            JsonNode properties = jsonNode.get("properties");
            if (properties == null) {
                continue;
            }
            count++;
//            String event = jsonNode.get("event").asText();
//            if ("SubmitOrder".equals(event) || "OrderPaid".equals(event)) {
////                String total_amount = properties.get("total_amount").asText();
////                System.out.println(event + " " + total_amount);
//                count++;
//            }
        }

        System.out.println(count);

        reader.close();
    }

    @SneakyThrows
    @Test
    public void testPageView() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);

        BufferedReader reader = new BufferedReader(new FileReader("/Users/lkzc19/Projects/startorch/g_chaumet/kafka_event/PageView.log"));

        int i = 10;

        String line;
        while ((line = reader.readLine()) != null) {
            if (i-- == 0) {
                break;
            }
            ProducerRecord<String, String> record = new ProducerRecord<>("event_topic", null, line);
            producer.send(record);
        }

        reader.close();
        producer.close();
    }

    @SneakyThrows
    @Test
    public void testOrderPaid() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);

        BufferedReader reader = new BufferedReader(new FileReader("/Users/lkzc19/Projects/startorch/g_chaumet/kafka_event/OrderPaid.log"));

        int i = 10;

        String line;
        while ((line = reader.readLine()) != null) {
//            if (i-- == 0) {
//                break;
//            }
//            System.out.println(line);
            ProducerRecord<String, String> record = new ProducerRecord<>("event_topic", null, line);
            producer.send(record);
            TimeUnit.SECONDS.sleep(1);
        }

        reader.close();
        producer.close();
    }
}