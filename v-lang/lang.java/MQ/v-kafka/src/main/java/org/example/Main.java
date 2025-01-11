package org.example;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        KafkaConsumer<String, String> consumer = getStringStringKafkaConsumer();

        try {
            while(true) {
                ConsumerRecords<String, String> poll = consumer.poll(Duration.ofSeconds(10));
                for (TopicPartition topicPartition : poll.partitions()) {

                    //	通过TopicPartition获取指定的消息集合，获取到的就是当前topicPartition下面所有的消息
                    List<ConsumerRecord<String, String>> partitionRecords = poll.records(topicPartition);

                    //	获取TopicPartition对应的主题名称
                    String topic = topicPartition.topic();
                    //	获取TopicPartition对应的分区位置
                    int partition = topicPartition.partition();
                    //	获取当前TopicPartition下的消息条数
                    int size = partitionRecords.size();
                    System.out.printf("--- 获取topic: %s, 分区位置：%s, 消息总数： %s%n", topic, partition, size);

                    for(int i = 0; i < size; i++) {
                        ConsumerRecord<String, String> consumerRecord = partitionRecords.get(i);
                        //	实际的数据内容
                        String key = consumerRecord.key();
                        //	实际的数据内容
                        String value = consumerRecord.value();
                        //	当前获取的消息偏移量
                        long offset = consumerRecord.offset();
                        //	表示下一次从什么位置(offset)拉取消息
                        long commitOffser = offset + 1;
                        System.out.printf("消费消息 key：%s, value：%s, 消息offset: %s, 提交offset: %s%n", key, value, offset, commitOffser);
                        System.out.println((int) (offset % (100 * 10000) * 100 + partition));
                        Thread.sleep(1500);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }

    private static KafkaConsumer<String, String> getStringStringKafkaConsumer() {
        Properties prop = new Properties();

        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"127.0.0.1:9092");
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        prop.put(ConsumerConfig.GROUP_ID_CONFIG, "con-1");    // 消费者组
        prop.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
        prop.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true);    //自动提交偏移量
        prop.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1000);     //自动提交时间

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(prop);
        ArrayList<String> topics = new ArrayList<>();
        //可以订阅多个消息
        topics.add("nahida");
        consumer.subscribe(topics);
        return consumer;
    }
}
