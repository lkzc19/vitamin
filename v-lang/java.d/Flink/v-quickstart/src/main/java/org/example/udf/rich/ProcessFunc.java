package org.example.udf.rich;

import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.util.Collector;
import org.example.model.WaterSensor;

public class ProcessFunc extends ProcessFunction<WaterSensor, WaterSensor> {

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
    }

    @Override
    public void close() throws Exception {
        super.close();
    }

    @Override
    public void processElement(
            WaterSensor waterSensor,
            ProcessFunction<WaterSensor, WaterSensor>.Context context,
            Collector<WaterSensor> collector
    ) throws Exception {

    }
}
