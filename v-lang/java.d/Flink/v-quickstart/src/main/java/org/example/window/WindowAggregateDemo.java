package org.example.window;

import org.apache.flink.api.common.functions.AggregateFunction;
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
public class WindowAggregateDemo {
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

        // 输入类型 中间结果类型 输出类型
        SingleOutputStreamOperator<String> aggregate = sensorWS.aggregate(new AggregateFunction<WaterSensor, Integer, String>() {
            @Override
            public Integer createAccumulator() {
                System.out.println("创建累加器");
                return 0;
            }

            @Override
            public Integer add(WaterSensor waterSensor, Integer accumulator) {
                System.out.println("累加 " + waterSensor.getVc());
                return accumulator + waterSensor.getVc();
            }

            @Override
            public String getResult(Integer accumulator) {
                System.out.println("获取最终结果");
                return "最终结果：" + accumulator;
            }

            // 只有会话窗口才会用到
            @Override
            public Integer merge(Integer integer, Integer acc1) {
                System.out.println("merge");
                return 0;
            }
        });

        aggregate.print();

        env.execute();
    }
}
