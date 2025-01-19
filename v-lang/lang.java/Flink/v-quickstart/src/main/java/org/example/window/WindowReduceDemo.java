package org.example.window;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.example.model.WaterSensor;

/**
 * 增量窗口
 */
public class WindowReduceDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<WaterSensor> sensorDS = env.fromElements(
                new WaterSensor("s1", 1L, 1),
                new WaterSensor("s1", 2L, 3),
                new WaterSensor("s1", 3L, 3),
                new WaterSensor("s1", 3L, 3),
                new WaterSensor("s1", 3L, 3),
                new WaterSensor("s1", 3L, 3),
                new WaterSensor("s1", 3L, 3)
        );

        KeyedStream<WaterSensor, String> sensorKS = sensorDS.keyBy(WaterSensor::getId);

        WindowedStream<WaterSensor, String, GlobalWindow> sensorWS = sensorKS.countWindow(5);

        SingleOutputStreamOperator<WaterSensor> reduce = sensorWS.reduce((v1, v2) -> { // 两两聚合 不足两条不会进入该方法处理
            System.out.println("value1: " + v1);
            System.out.println("value2: " + v2);
            // 这条会和下一条再进入该方法进行处理
            return new WaterSensor(v1.getId(), v2.getTs(), v1.getVc() + v2.getVc());
        });

        reduce.print();

        env.execute();
    }
}
