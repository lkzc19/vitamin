package org.example;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.example.model.WaterSensor;
import org.example.udf.FilterFunc;

public class Main {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<WaterSensor> ds = env.fromElements(
                new WaterSensor("s1", 1L, 1),
                new WaterSensor("s1", 2L, 11),
                new WaterSensor("s2", 3L, 5),
                new WaterSensor("s3", 4L, 6)
        );

//        SingleOutputStreamOperator<WaterSensor> filter = ds.filter(it -> "s1".equals(it.getId()));


        SingleOutputStreamOperator<WaterSensor> filter = ds.filter(new FilterFunc("s1"));

        filter.print();
        env.execute();
    }
}