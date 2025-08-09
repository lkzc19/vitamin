package org.example;

import lombok.SneakyThrows;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.hadoop.ParquetFileReader;
import org.apache.parquet.hadoop.metadata.ParquetMetadata;
import org.apache.parquet.schema.MessageType;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class MainTest {

    @SneakyThrows
    @Test
    public void test() {
        String filePath = "data/data.parquet";
        Configuration configuration = new Configuration();

        Path path = new Path(filePath);
        ParquetMetadata metadata = ParquetFileReader.readFooter(configuration, path);
        MessageType schema = metadata.getFileMetaData().getSchema();

        Set<String> d = new HashSet<>();

        schema.getColumns().forEach(column -> {
            String name = column.getPath()[0];
            boolean exist = d.add(name);
            if (!exist) {
                System.out.println(name);
            }
        });
    }
}