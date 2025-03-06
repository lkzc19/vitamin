package org.example.vtils.lettuce;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class Main {
    public static void main(String[] args) {
        // 创建 RedisClient
        RedisClient client = RedisClient.create("redis://localhost:6379");
        // 获取连接
        StatefulRedisConnection<String, String> connection = client.connect();
        // 获取同步命令接口
        RedisCommands<String, String> commands = connection.sync();
        // 执行命令
        commands.set("key", "Hello, Lettuce!");
        String value = commands.get("key");
        System.out.println("Value: " + value);
        // 关闭连接
        connection.close();
        client.shutdown();
    }
}
