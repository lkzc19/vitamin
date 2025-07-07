package org.example.source;

import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public class SocketSourceDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // nc -lk 7777
        env
                .socketTextStream("localhost", 7777)
                .print();

        env.execute();
    }
}
