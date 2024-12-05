package org.example.udf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.flink.api.common.functions.FilterFunction;
import org.example.model.WaterSensor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FilterFunc implements FilterFunction<WaterSensor> {

    private String id;

    @Override
    public boolean filter(WaterSensor waterSensor) {
        return this.id.equals(waterSensor.getId());
    }
}
