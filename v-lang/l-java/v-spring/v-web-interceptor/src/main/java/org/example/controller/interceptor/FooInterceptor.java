package org.example.controller.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.example.anno.Foo;
import org.example.exception.BizException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class FooInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            log.info("UnSupport handler");
            return true;
        }

        Foo foo = ((HandlerMethod) handler).getMethodAnnotation(Foo.class);

        if (foo != null) {
            return true;
        } else {
            throw new BizException();
        }
    }
}
