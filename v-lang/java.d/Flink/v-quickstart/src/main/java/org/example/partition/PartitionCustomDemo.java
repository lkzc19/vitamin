package org.example.partition;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.example.model.WaterSensor;

/**
 * 自定义分区器
 */
public class PartitionCustomDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);

        DataStreamSource<WaterSensor> sensorDS = env.fromElements(
                new WaterSensor("s1", 1L, 1),
                new WaterSensor("s2", 2L, 3),
                new WaterSensor("s2", 2L, 3),
                new WaterSensor("s2", 2L, 3),
                new WaterSensor("s2", 2L, 3),
                new WaterSensor("s2", 2L, 3),
                new WaterSensor("s2", 2L, 3),
                new WaterSensor("s2", 2L, 3),
                new WaterSensor("s3", 3L, 3)
        );

        // 第二个参数返回的值作为 第一个参数的入参
        sensorDS.partitionCustom(new MyPartitioner(), it -> it);

        env.execute();
    }
}
