package org.example.aggregate;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.example.model.WaterSensor;
import org.example.udf.KeyByFunc;

public class SimpleAggregateDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(1);

        DataStreamSource<WaterSensor> sensorDS = env.fromElements(
                new WaterSensor("s1", 1L, 1),
                new WaterSensor("s2", 2L, 3),
                new WaterSensor("s2", 3L, 2),
                new WaterSensor("s2", 4L, 4),
                new WaterSensor("s3", 3L, 3)
        );

        KeyedStream<WaterSensor, String> sensorKS = sensorDS.keyBy(new KeyByFunc());

//        SingleOutputStreamOperator<WaterSensor> sum = sensorKS.sum("vc");
//        sum.print();

        /*
            min/minBy
                min 只取指定值
                minBy 取整条值
         */
//        SingleOutputStreamOperator<WaterSensor> min = sensorKS.min("vc");
//        min.print();

        SingleOutputStreamOperator<WaterSensor> minBy = sensorKS.minBy("vc");
        minBy.print();

        env.execute();
    }
}
