package org.example.udf;

import org.apache.flink.api.java.functions.KeySelector;
import org.example.model.WaterSensor;

public class KeyByFunc implements KeySelector<WaterSensor, String> {

    @Override
    public String getKey(WaterSensor waterSensor) throws Exception {
        return waterSensor.getId();
    }
}
