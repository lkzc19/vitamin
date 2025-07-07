package org.example.udf.rich;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.flink.api.common.functions.RichFilterFunction;
import org.apache.flink.configuration.Configuration;
import org.example.model.WaterSensor;

@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RichFilterFunc extends RichFilterFunction<WaterSensor> {

    private int vc;

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        String index = "子任务编号=" + getRuntimeContext().getIndexOfThisSubtask();
        String name = "子任务名称=" + getRuntimeContext().getTaskNameWithSubtasks();
        System.out.println("open " + index + " " + name);
    }

    @Override
    public void close() throws Exception {
        super.close();
        String index = "子任务编号=" + getRuntimeContext().getIndexOfThisSubtask();
        String name = "子任务名称=" + getRuntimeContext().getTaskNameWithSubtasks();
        System.out.println("close " + index + " " + name);
    }

    @Override
    public boolean filter(WaterSensor waterSensor) throws Exception {
        return waterSensor.getVc() > vc;
    }
}
