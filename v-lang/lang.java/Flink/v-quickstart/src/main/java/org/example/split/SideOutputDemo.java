package org.example.split;

import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.apache.flink.util.OutputTag;
import org.example.model.WaterSensor;
import org.example.partition.MyPartitioner;

/**
 * 测流
 */
public class SideOutputDemo {
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

        OutputTag<WaterSensor> s2 = new OutputTag<>("s2", Types.POJO(WaterSensor.class));

        SingleOutputStreamOperator<WaterSensor> process = sensorDS.process(new ProcessFunction<WaterSensor, WaterSensor>() {
            @Override
            public void processElement(WaterSensor value, ProcessFunction<WaterSensor, WaterSensor>.Context ctx, Collector<WaterSensor> out) throws Exception {
                String id = value.getId();
                if (id.equals("s2")) {
                    ctx.output(s2, value); // 侧流
                } else {
                    out.collect(value); // 主流
                }
            }
        });

        process.print("主流");
        process.getSideOutput(s2).print("侧流");

        env.execute();
    }
}
