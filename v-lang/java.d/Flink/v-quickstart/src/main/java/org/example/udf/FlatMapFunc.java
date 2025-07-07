package org.example.udf;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;
import org.example.model.WaterSensor;

public class FlatMapFunc implements FlatMapFunction<WaterSensor, String> {

    @Override
    public void flatMap(WaterSensor waterSensor, Collector<String> collector) throws Exception {
        if ("s1".equals(waterSensor.getId())) {
            collector.collect("xxx");
        } else {
            collector.collect("yyy");
            collector.collect("zzz");
        }
    }
}
