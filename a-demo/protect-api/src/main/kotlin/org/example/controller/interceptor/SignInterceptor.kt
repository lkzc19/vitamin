package org.example.controller.interceptor

import jakarta.servlet.ReadListener
import jakarta.servlet.ServletInputStream
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletRequestWrapper
import jakarta.servlet.http.HttpServletResponse
import org.example.Cache
import org.example.util.JsonUtils
import org.springframework.http.MediaType
import org.springframework.util.DigestUtils
import org.springframework.util.StreamUtils
import org.springframework.web.servlet.HandlerInterceptor
import java.io.BufferedReader
import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader
import java.net.URLDecoder
import java.util.*


class SignInterceptor : HandlerInterceptor {
    
    override fun preHandle(request: HttpServletRequest, response: HttpServletResponse, handler: Any): Boolean {
        println("SignInterceptor...")
        
        val sign = request.getHeader("X-Sign") ?: run {
            responseFail(response, "[X-Sign]缺失")
            return false
        }
        val timestamp = request.getHeader("X-Timestamp")?.toLong() ?: run {
            responseFail(response, "[X-Timestamp]缺失")
            return false
        }
        val nonce = request.getHeader("X-Nonce") ?: run {
            responseFail(response, "[X-Nonce]缺失")
            return false
        }
        
        /*
         * 1. 重放验证
         * 判断timestamp时间戳与当前时间是否操过60s(过期时间根据业务情况设置),如果超过了就提示签名过期。
         */
        val now = System.currentTimeMillis()
        if (now - timestamp > 60 * 1000) {
            responseFail(response, "请求过期")
            return false
        }
        
        // 2. 判断nonce
        val nonceKey = { it: String -> "nonce:$it" }
        
        if (Cache.exists(nonceKey(nonce))) {
            responseFail(response, "请求重复")
            return false
        } else {
            Cache.put(nonceKey(nonce))
        }
        
        // 3. 签名验证
        val params = SignRequestWrapper(request).getParams()
        val signHeader = SignHeader(sign = sign, timestamp = timestamp, nonce = nonce)
        if (!verifySign(params, signHeader)) {
            responseFail(response, "签名错误")
            return false
        }
        
        return true
    }
    
    private fun responseFail(response: HttpServletResponse, message: String) {
        response.status = HttpServletResponse.SC_BAD_REQUEST
        response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
        response.writer.write(message)
    }
    
    private fun verifySign(params: SortedMap<String, String>, signHeader: SignHeader): Boolean {
        val tmp = signHeader.nonce + signHeader.timestamp + JsonUtils.object2Json(params)
        println(tmp)
        val sign = DigestUtils.md5DigestAsHex(tmp.toByteArray()).uppercase(Locale.getDefault())
        println(sign)
        return sign == signHeader.sign
    }
}

data class SignHeader (
    val sign: String,
    val timestamp: Long,
    val nonce: String,
)

class SignRequestWrapper(request: HttpServletRequest) : HttpServletRequestWrapper(request) {
    //用于将流保存下来
    private var requestBody: ByteArray? = null
    
    init {
        requestBody = StreamUtils.copyToByteArray(request.inputStream)
    }
    
    @Throws(IOException::class)
    override fun getInputStream(): ServletInputStream {
        val bais = ByteArrayInputStream(requestBody)
        return object : ServletInputStream() {
            override fun isFinished(): Boolean {
                return false
            }
            
            override fun isReady(): Boolean {
                return false
            }
            
            override fun setReadListener(readListener: ReadListener) {}
            
            @Throws(IOException::class)
            override fun read(): Int {
                return bais.read()
            }
        }
    }
    
    @Throws(IOException::class)
    override fun getReader(): BufferedReader {
        return BufferedReader(InputStreamReader(getInputStream()))
    }

    /**
     * 简单demo body没有嵌套处理
     */
    fun getParams(): SortedMap<String, String> {
        val result = TreeMap<String, String>()
        
        // body
        val body = String(requestBody!!)
        JsonUtils.json2Object(body, Map::class.java)
            .mapKeys { (key, _) -> key.toString() }
            .mapValues { (_, value) -> value.toString() }
            .let { result.putAll(it) }
        if (this.queryString == null) {
            return result
        }
        // query
        URLDecoder.decode(this.queryString, "utf-8").split("&").forEach {
            val kv = it.split("=")
            result[kv[0]] = kv[1]
        }
        
        return result
    }
}
