package org.example.env;

import org.apache.flink.api.common.RuntimeExecutionMode;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class EnvDemo {

    public static void main(String[] args) {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // 一般启动时通过命令参数指定
        env.setRuntimeMode(RuntimeExecutionMode.BATCH);
    }
}
