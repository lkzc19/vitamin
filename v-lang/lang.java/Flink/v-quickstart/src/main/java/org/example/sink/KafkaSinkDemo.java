package org.example.sink;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.*;

public class KafkaSinkDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 精准一次 必须开启checkpoint
        env.enableCheckpointing(2000, CheckpointingMode.EXACTLY_ONCE);

        List<String> list = Arrays.asList("I am spark's dog", "I am spark's dog", "I am spark's dog");
        DataStreamSource<String> source = env.fromCollection(list);

        Properties props = new Properties();
        props.put(ProducerConfig.TRANSACTION_TIMEOUT_CONFIG, 10 * 60 * 1000);

        KafkaSink<String> kafkaSink = KafkaSink.<String>builder()
                .setBootstrapServers("localhost:9092")
                .setRecordSerializer(
                        KafkaRecordSerializationSchema.<String>builder()
                                .setTopic("flink_test")
                                .setValueSerializationSchema(new SimpleStringSchema())
                                .build()
                ).setDeliverGuarantee(DeliveryGuarantee.AT_LEAST_ONCE) // 至少一次
                .setTransactionalIdPrefix("flink-") // 精准一次 必须设置事物前缀
                .setKafkaProducerConfig(props) // 精准一次 设置事物属性
                .build();

        source.sinkTo(kafkaSink);

        env.execute();
    }
}
