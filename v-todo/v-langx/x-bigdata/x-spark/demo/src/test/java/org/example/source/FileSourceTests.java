package org.example.source;

import org.apache.spark.sql.*;
import org.junit.Test;
import scala.collection.immutable.Seq;

import static org.apache.spark.sql.functions.get_json_object;

public class FileSourceTests {

    SparkSession spark = SparkSession.builder()
            .appName("FileSourceTests")
            .master("local[*]")
            .getOrCreate();

    @Test
    public void txt() {
        Dataset<String> data = spark.read().textFile("data/input/input.txt");
        data.printSchema();
        data.show();
    }

    @Test
    public void json() {
        Dataset<Row> data = spark.read().json("data/input/input.json");
        data.printSchema();
        data.createOrReplaceTempView("input");
        Dataset<Row> df = spark.read().table("input");
        df.show();
    }

    @Test
    public void json_log() {
        Dataset<Row> data = spark.read().json("data/input/input.log");
        data.show();
    }

    @Test
    public void csv() {
        Dataset<Row> data = spark.read().option("header", "true").csv("data/input/input.csv");
        data.show();
    }
}