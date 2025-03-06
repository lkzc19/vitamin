package org.example.vtils.jedis;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Slf4j
public class RedisClient {

    private final JedisPool jedisPool;

    public RedisClient() {
        jedisPool = new JedisPool();
        Jedis jedis = jedisPool.getResource();
        log.info(jedis.ping());
    }

    public RedisClient(JedisPoolConfig config, String host, int port, String password, int timeout) {
        jedisPool = new JedisPool(config, host, port, timeout, password);
    }

    protected Jedis getJedis() {
        return jedisPool.getResource();
    }

    public void ping() {
        Jedis jedis = getJedis();
    }

    public String get(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        } catch (Exception e){
            log.error("get命令操作失败，请求参数：{}", key,e);
        }
        return null;
    }
}
