package org.example.controller.interceptor

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.example.model.SignHeader
import org.springframework.cache.annotation.Cacheable
import org.springframework.cache.annotation.Caching
import org.springframework.http.MediaType
import org.springframework.web.servlet.HandlerInterceptor


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

        // 获取请求头参数
        val signHeader = SignHeader(sign = sign, timestamp = timestamp, nonce = nonce)

        /*
         * 1.重放验证
         * 判断timestamp时间戳与当前时间是否操过60s(过期时间根据业务情况设置),如果超过了就提示签名过期。
         */
        val now = System.currentTimeMillis()
        if (now - timestamp > 60 * 1000) {
            responseFail(response, "请求过期")
            return false
        }

//        val nonceExists = redisUtil.hasKey(NONCE_KEY + requestHeader.getNonce())
//        if (nonceExists) {
//            //请求重复
//            responseFail(httpResponse, ReturnCode.REPLAY_ERROR)
//            return
//        } else {
//            redisUtil.set(NONCE_KEY + requestHeader.getNonce(), requestHeader.getNonce(), signMaxTime)
//        }

        return true
    }

    private fun responseFail(response: HttpServletResponse, message: String) {
        response.status = HttpServletResponse.SC_BAD_REQUEST
        response.contentType = MediaType.APPLICATION_JSON_UTF8_VALUE
        response.writer.write(message)
    }


//    @Caching(
//        cacheable = @Cacheable(value = "say", cacheManager = "caffeineCacheManager", key = "'p_' + #name"),
//
//    )
//    private fun cachingNonce(nonce: String): Boolean {
//
//    }
}