package org.example.partition;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.example.model.WaterSensor;

/**
 * 7 自带分区器 + 1 自定义分区器
 */
public class PartitionDemo {
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

        // 随机分区
//        sensorDS.shuffle().print();

        // 轮询 解决数据倾斜的问题
//        sensorDS.rebalance().print();

        // 局部轮询
//        sensorDS.rescale().print();

        // 广播 下游每个算子都给数据
//        sensorDS.broadcast().print();

        // 全局 全部发往 第一个子任务 强行将并行度变为1
        sensorDS.global().print();

        // keyBy
        // one-to-one

        env.execute();
    }
}
