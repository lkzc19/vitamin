package org.example.vtils.jedis.demo;

import redis.clients.jedis.Jedis;

import java.util.Set;

public class Demo1 {

    public void print() {
        Jedis jedis = new Jedis("10.245.23.216", 6178);
        jedis.auth("SensorsData2015");

        jedis.select(1);

        Set<String> keys = jedis.keys("webhook:apppush:*");
        for (String key : keys) {
            System.out.println(jedis.get(key));
        }

        jedis.close();
    }
}
