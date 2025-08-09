package org.example;

import lombok.SneakyThrows;
import org.apache.htrace.shaded.fasterxml.jackson.databind.JsonNode;
import org.apache.htrace.shaded.fasterxml.jackson.databind.ObjectMapper;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.junit.Test;

public class MainTest {

    @SneakyThrows
    @Test
    public void testMain() {
        SparkSession spark = SparkSession.builder()
                .appName("Spark Example")
                .master("local[*]")
                .config("spark.driver.host", "127.0.0.1")  // 使用本地回环地址
                .config("spark.driver.bindAddress", "127.0.0.1")  // 显式设置绑定地址
                .getOrCreate();

        Dataset<Row> df = spark.read().parquet("data/data.parquet");
        df.show(10, false);
        df.createTempView("source");

        Dataset<String> jsonStrings = df.toJSON();

        ObjectMapper mapper = new ObjectMapper();
        jsonStrings.foreach(jsonStr -> {
            mapper.readTree(jsonStr);
        });

        spark.stop();
    }
}