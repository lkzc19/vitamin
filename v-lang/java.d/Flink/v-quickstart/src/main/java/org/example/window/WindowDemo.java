package org.example.window;

import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.GlobalWindows;
import org.apache.flink.streaming.api.windowing.assigners.ProcessingTimeSessionWindows;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.example.model.WaterSensor;

public class WindowDemo {
    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        DataStreamSource<WaterSensor> sensorDS = env.fromElements(
                new WaterSensor("s1", 1L, 1),
                new WaterSensor("s2", 2L, 3),
                new WaterSensor("s3", 3L, 3)
        );

        KeyedStream<WaterSensor, String> sensorKS = sensorDS.keyBy(WaterSensor::getId);

        // 并行度只有 1
//        sensorDS.windowAll()
        // 每个key搜是 1 个并行度
//        sensorKS.window()

        // 基于时间
        sensorKS.window(TumblingEventTimeWindows.of(Time.seconds(10))); // 滚动窗口 窗口长度10s
        sensorKS.window(SlidingProcessingTimeWindows.of(Time.seconds(10), Time.seconds(2))); // 滑动窗口 窗口长度10s 滑动步长2s
        sensorKS.window(ProcessingTimeSessionWindows.withGap(Time.seconds(5))); // 会话窗口 超时间隔5s

        // 基于计数
        sensorKS.countWindow(5); // 滚动窗口 窗口长度5个元素
        sensorKS.countWindow(5, 2); // 滑动窗口 窗口长度5个元素 滑动步长2个元素
        sensorKS.window(GlobalWindows.create()); // 全局窗口 需要自定义

        sensorDS.print();

    }
}
