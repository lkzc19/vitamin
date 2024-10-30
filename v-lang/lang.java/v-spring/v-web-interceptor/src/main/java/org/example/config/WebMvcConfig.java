package org.example.config;

import jakarta.annotation.Resource;
import org.example.controller.interceptor.FooInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Resource
    private FooInterceptor fooInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(fooInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/ping");
    }
}
