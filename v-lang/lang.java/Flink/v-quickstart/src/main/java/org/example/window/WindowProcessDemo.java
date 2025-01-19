package org.example.window;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.GlobalWindow;
import org.apache.flink.util.Collector;
import org.example.model.WaterSensor;

/**
 * 全窗口
 */
public class WindowProcessDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<WaterSensor> sensorDS = env.fromElements(
                new WaterSensor("s1", 1L, 1),
                new WaterSensor("s1", 2L, 3),
                new WaterSensor("s2", 3L, 3),
                new WaterSensor("s1", 3L, 3),
                new WaterSensor("s2", 3L, 3),
                new WaterSensor("s2", 3L, 3),
                new WaterSensor("s2", 3L, 3),
                new WaterSensor("s2", 3L, 3),
                new WaterSensor("s1", 3L, 3),
                new WaterSensor("s1", 3L, 3)
        );

        KeyedStream<WaterSensor, String> sensorKS = sensorDS.keyBy(WaterSensor::getId);

        WindowedStream<WaterSensor, String, GlobalWindow> sensorWS = sensorKS.countWindow(5);

        SingleOutputStreamOperator<String> process = sensorWS.process(new ProcessWindowFunction<WaterSensor, String, String, GlobalWindow>() {
            @Override
            public void process(String key, ProcessWindowFunction<WaterSensor, String, String, GlobalWindow>.Context context, Iterable<WaterSensor> elements, Collector<String> out) throws Exception {
                int v = 0;
                for (WaterSensor element : elements) {
                    v = v + element.getVc();
                }
                out.collect(key + "  " + v);
            }
        });

        process.print();

        env.execute();
    }
}
