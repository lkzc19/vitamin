package org.example.udf;

import org.apache.flink.api.common.functions.MapFunction;
import org.example.model.WaterSensor;

public class MapFunc implements MapFunction<WaterSensor, String> {

    @Override
    public String map(WaterSensor waterSensor) throws Exception {
        return waterSensor.getId();
    }
}