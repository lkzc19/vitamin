package org.example.sink;

import org.apache.flink.api.common.serialization.SimpleStringEncoder;
import org.apache.flink.connector.file.sink.FileSink;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.filesystem.OutputFileConfig;
import org.apache.flink.streaming.api.functions.sink.filesystem.bucketassigners.DateTimeBucketAssigner;
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy;

import java.time.Duration;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;

public class FileSinkDemo {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        env.enableCheckpointing(2000, CheckpointingMode.EXACTLY_ONCE);

        List<String> list = Arrays.asList("I am spark's dog", "I am spark's dog", "I am spark's dog");
        DataStreamSource<String> source = env.fromCollection(list);

        FileSink<String> fileSink = FileSink
                .forRowFormat(new Path("output"), new SimpleStringEncoder("UTF-8"))
                .withOutputFileConfig(
                        OutputFileConfig.builder()
                                .withPartPrefix("data")
                                .withPartSuffix(".txt")
                                .build()
                )
                .withBucketAssigner(new DateTimeBucketAssigner("yyyy-MM-dd", ZoneId.systemDefault()))
                .withRollingPolicy(
                        DefaultRollingPolicy.builder()
                                .withRolloverInterval(10)
                                .withMaxPartSize(1024 * 1024)
                                .build()
                )
                .build();

        source.sinkTo(fileSink);

        env.execute();
    }
}
