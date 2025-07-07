package org.example.aggregate;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.example.model.WaterSensor;
import org.example.udf.KeyByFunc;

public class KeyByDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<WaterSensor> sensorDS = env.fromElements(
                new WaterSensor("s1", 1L, 1),
                new WaterSensor("s2", 2L, 3),
                new WaterSensor("s2", 2L, 3),
                new WaterSensor("s3", 3L, 3)
        );

        /*
          keyBy 非转换算子
          将数据分组 同一组一定在同一个分区(线程) 一个分区可以有多个组
         */
        KeyedStream<WaterSensor, String> sensorKS = sensorDS.keyBy(new KeyByFunc());

        sensorKS.print();

        env.execute();
    }
}
