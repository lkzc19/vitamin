package org.example.udf.rich;

import org.apache.flink.api.common.functions.RichMapFunction;
import org.apache.flink.configuration.Configuration;
import org.example.model.WaterSensor;

public class RichMapFunc extends RichMapFunction<WaterSensor, WaterSensor> {

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
    }

    @Override
    public void close() throws Exception {
        super.close();
    }

    @Override
    public WaterSensor map(WaterSensor waterSensor) throws Exception {
        return new WaterSensor(waterSensor.getId(), waterSensor.getTs(), waterSensor.getVc() + 42);
    }
}
