package org.example.aggregate;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.example.model.WaterSensor;
import org.example.udf.KeyByFunc;

public class ReduceDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStreamSource<WaterSensor> sensorDS = env.fromElements(
                new WaterSensor("s1", 1L, 1),
                new WaterSensor("s2", 2L, 3),
                new WaterSensor("s2", 3L, 3),
                new WaterSensor("s2", 4L, 3),
                new WaterSensor("s2", 5L, 3),
                new WaterSensor("s3", 3L, 3)
        );

        SingleOutputStreamOperator<WaterSensor> reduce = sensorDS
                .keyBy(new KeyByFunc())
                .reduce((v1, v2) -> { // 两两聚合 不足两条不会进入该方法处理
                    System.out.println("value1: " + v1);
                    System.out.println("value2: " + v2);
                    // 这条会和下一条再进入该方法进行处理
                    return new WaterSensor(v1.getId(), v2.getTs(), v1.getVc() + v2.getVc());
                });

        reduce.print();

        env.execute();
    }
}
