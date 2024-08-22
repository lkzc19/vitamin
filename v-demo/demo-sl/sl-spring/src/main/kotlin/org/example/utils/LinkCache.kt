package org.example.utils

import com.github.benmanes.caffeine.cache.Caffeine
import java.util.concurrent.TimeUnit

/**
 * 用于保存 短码与长链的映射关系
 */
object LinkCache {

    // 短码 -> 长链
    private val cache = buildCache()
    // 长链 -> 短码
    private val ehcac = buildCache()

    operator fun get(k: String): String? {
        val v = cache.getIfPresent(k)
        return v.apply { this?.let { cache.getIfPresent(k) } } // 保证两个相关联的kv使用频率一致
    }

    operator fun set(k: String, v: String) {
        cache.put(k, v)
        ehcac.put(v, k)
    }

    // Reverse get
    fun ret(k: String): String? {
        val v = ehcac.getIfPresent(k)
        return v.apply { this?.let { cache.getIfPresent(k) } } // 保证两个相关联的kv使用频率一致
    }

    private fun buildCache() = Caffeine.newBuilder()
        .initialCapacity(10)
        .maximumSize(1000)
        .expireAfterWrite(30, TimeUnit.MINUTES)
        .build<String, String>()
}