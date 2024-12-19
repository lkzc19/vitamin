package org.example.collect.controller.interceptor;

import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class HeaderInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) throws IOException {
        Map<String, Object> map = new LinkedHashMap<>();

        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            String value = request.getHeader(key.toLowerCase());
            map.put(key, value);
        }

        Map<String, String> query = new LinkedHashMap<>();

        String queryString = request.getQueryString(); // 获取查询字符串
        if (queryString != null) {
            String[] params = queryString.split("&");
            for (String param : params) {
                String[] keyValue = param.split("=");
                if (keyValue.length == 2) {
                    String key = keyValue[0];
                    String value = keyValue[1];
                    // 可以在这里处理查询参数
                    query.put(key, value);
                }
            }
        }

//        String compress = (String) map.get("x-compress-codec");
//        if (!StringUtils.hasText(compress)) {
//            return false;
//        }
//        String ua = (String) map.get("user-agent");
//        if ("SLBHealthCheck".equals(ua)) {
//            return false;
//        }

        System.out.println("HeaderInterceptor");
        new RequestWrapper3(request);


        return true;
    }
}
