package org.example.vtils.jedis;

import org.junit.Test;

import java.util.ArrayList;

public class RedisClientTest {

    @Test
    public void test() {
        RedisClient client = new RedisClient();
        System.out.println(client.get("hello"));
    }

    @Test
    public void test1() {
        ArrayList<String> strings = new ArrayList<>();
        strings.add("hello");
        strings.add("world");
        strings.add("foo");

        strings.stream().filter("hello"::equals).forEach(System.out::println);
    }
}
