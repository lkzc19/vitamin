#!/bin/bash

# 使用 while true 创建一个无限循环
while true; do
    echo "xxx" | kafka-console-producer --topic flink_test --bootstrap-server localhost:9092
    sleep 1  # 等待1秒钟，以避免占用过多的CPU资源
done