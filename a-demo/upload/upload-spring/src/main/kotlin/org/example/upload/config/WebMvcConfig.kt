package org.example.upload.config

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

/**
 * 使用配置的方式设置静态文件位置
 */
//@Configuration
class WebMvcConfig : WebMvcConfigurationSupport() {

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        //将所有F:/resources/目录下的资源,访问时都映射到/res/** 路径下
        registry
            .addResourceHandler("/file/**")
            .addResourceLocations("file:C:/tmp/")
    }
}