package org.example

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.util.concurrent.TimeUnit

@SpringBootApplication
class ProtectApiApplication

fun main(args: Array<String>) {
	runApplication<ProtectApiApplication>(*args)
}

/**
 * 只是用来做demo使用
 * 用来判断nonce是否存在
 */
object Cache {
	
	private val nonce: Cache<String, Int> = Caffeine.newBuilder()
		.expireAfterWrite(1, TimeUnit.MINUTES)
		.build()
	
	fun exists(key: String): Boolean = nonce.getIfPresent(key) != null
	
	fun put(key: String) = nonce.put(key, 1)
}
