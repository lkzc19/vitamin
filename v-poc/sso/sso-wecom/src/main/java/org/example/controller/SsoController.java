package org.example.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@Slf4j
public class SsoController {

    @GetMapping("/api/sso/login")
    public void login(HttpServletRequest request) {
        log.info("cookie ===============================================================");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                System.out.println(cookie.getName() + ": " + cookie.getValue());
            }
        }
        log.info("cookie ===============================================================");
        log.info("param ===============================================================");
        request.getParameterMap().forEach((key, value) -> System.out.println(key + ": " + Arrays.toString(value)));
        log.info("param ===============================================================");
    }
}
