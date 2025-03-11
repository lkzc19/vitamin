package org.example;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        // 创建SparkConf对象
        SparkConf conf = new SparkConf()
                .setAppName("WordCount")
                .setMaster("local");

        // 创建JavaSparkContext对象
        JavaSparkContext sc = new JavaSparkContext(conf);

        // 读取文本文件
        JavaRDD<String> lines = sc.textFile("input.txt");

        // 计算单词出现次数
        JavaRDD<String> words = lines.flatMap(line -> Arrays.asList(line.split(" ")).iterator());
        JavaRDD<String> filteredWords = words.filter(word -> !word.isEmpty());
        JavaPairRDD<String, Integer> wordCounts = filteredWords.mapToPair(word -> new Tuple2<>(word, 1))
                .reduceByKey((x, y) -> x + y);
        Map<String, Integer> wordCountsMap = wordCounts.collectAsMap();

        // 输出结果
        for (Map.Entry<String, Integer> entry : wordCountsMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // 关闭JavaSparkContext对象
        sc.close();
    }
}