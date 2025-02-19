package org.example.vtils.jedis;

import redis.clients.jedis.Jedis;

public class Main {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("10.245.23.216", 6178);
//        jedis.auth("xxx");
        String ping = jedis.ping();
        System.out.println(ping);
        jedis.close();
    }
}
