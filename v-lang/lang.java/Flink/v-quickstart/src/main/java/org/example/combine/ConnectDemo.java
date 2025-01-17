package org.example.combine;

import org.apache.flink.streaming.api.datastream.ConnectedStreams;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.CoMapFunction;

public class ConnectDemo {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.setParallelism(2);

        DataStreamSource<Integer> source1 = env.fromElements(1, 2, 3);
        DataStreamSource<String> source3 = env.fromElements("7", "8", "9");

        ConnectedStreams<Integer, String> connect = source1.connect(source3);

        SingleOutputStreamOperator<Integer> map = connect.map(new CoMapFunction<Integer, String, Integer>() {
            @Override
            public Integer map1(Integer value) throws Exception {
                return value;
            }

            @Override
            public Integer map2(String value) throws Exception {
                return Integer.parseInt(value);
            }
        });

        map.print();

        env.execute();
    }
}
