package org.example.config

import org.example.controller.interceptor.SignInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig : WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(SignInterceptor())
            .addPathPatterns("/**")
            .excludePathPatterns("/ping")
    }
}