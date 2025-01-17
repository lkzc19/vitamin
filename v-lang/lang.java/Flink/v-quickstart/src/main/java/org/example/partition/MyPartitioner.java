package org.example.partition;

import org.apache.flink.api.common.functions.Partitioner;
import org.example.model.WaterSensor;

public class MyPartitioner implements Partitioner<WaterSensor> {

    @Override
    public int partition(WaterSensor waterSensor, int numPartitions) {
        return (int) (waterSensor.getTs() % numPartitions);
    }
}
