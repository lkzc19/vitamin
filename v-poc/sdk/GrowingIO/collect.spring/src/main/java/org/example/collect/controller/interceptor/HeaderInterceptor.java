package org.example.collect.controller.interceptor;

import org.example.collect.utils.JsonUtils;
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
            String value = request.getHeader(key);
            map.put(key, value);
        }

        RequestWrapper requestWrapper = new RequestWrapper(request);
        String body = requestWrapper.getBody();
        map.put("Request Body", body);


        String ua = (String) map.get("user-agent");
        if ("SLBHealthCheck".equals(ua)) {
            return false;
        }

        System.out.println(JsonUtils.toJson(map));

        return true;
    }
}
