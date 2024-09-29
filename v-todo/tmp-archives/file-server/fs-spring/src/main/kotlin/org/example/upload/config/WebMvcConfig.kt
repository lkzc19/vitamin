package org.example.upload.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig : WebMvcConfigurer {
    
    override fun addCorsMappings(registry: CorsRegistry) {
        registry
            .addMapping("/**")
//            .allowCredentials(true) // 设置allowCredentials为true后，allowedOrigins就不能使用*，可以使用allowedOriginPatterns做通配
            .allowedOrigins("*")
            .allowedMethods("*")
            .allowedHeaders("*")
    }
}