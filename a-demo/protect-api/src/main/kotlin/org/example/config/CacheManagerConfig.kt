package org.example.config

import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.concurrent.TimeUnit


@EnableCaching
@Configuration
class CacheManagerConfig {

    @Bean("caffeineCacheManager")
    fun cacheManager(): CacheManager {
        val cacheManager = CaffeineCacheManager()
        // 只是拿这个代替一下Redis做demo 存放nonce
        val caffeine = Caffeine.newBuilder()
            .expireAfterWrite(1, TimeUnit.MINUTES) // 设置过期时间，写入后五分钟过期
            .initialCapacity(100) // 初始化缓存空间大小
            .maximumSize(200) // 最大的缓存条数
        cacheManager.setCaffeine(caffeine)
        return cacheManager
    }
}