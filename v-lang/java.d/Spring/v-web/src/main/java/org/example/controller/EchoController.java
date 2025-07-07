package org.example.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.time.Duration;
import java.util.LinkedHashMap;

@RestController
public class EchoController {

    @RequestMapping("/echo")
    public ResponseEntity<LinkedHashMap<String, Object>> echo(HttpServletRequest request) {
        LinkedHashMap<String, Object> vo = new LinkedHashMap<>();

        vo.put("method", request.getMethod());
        vo.put("uri", request.getRequestURI());
        vo.put("query", request.getQueryString());
        vo.put("cookies", request.getCookies());

        vo.put("parameter", request.getParameterMap());

        vo.put("parameter1", request.getParameter("foo"));


        return ResponseEntity.ok(vo);
    }

//    @RequestMapping("/redirect")
//    public ResponseEntity<LinkedHashMap<String, Object>> redirect(HttpServletRequest request) {
//
//        // 创建 Cookie
//        Cookie cookie = new Cookie("user_token", "abc123");
//        cookie.setMaxAge(3600); // 1小时过期
//        cookie.setHttpOnly(true);
//        cookie.setDomain("baidu.com");
//
//        // 设置 Header
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
//
//        // 进行重定向
//        return ResponseEntity.status(HttpStatus.FOUND)
//                .headers(headers)
//                .header("Location", "https://www.baidu.com")
//                .build();
//    }

    @GetMapping("/test")
    public ResponseEntity<String> test(HttpServletRequest request) {
        // 前置步骤 获取到 cookie sensorsdata-token

        ResponseCookie cookie = ResponseCookie.from("sensorsdata-token", "xxx")
//                .domain(".opple.com") // 父域名
                .path("/")
                .secure(true)
                .sameSite("None")
                .build();

        // 设置 Header
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, cookie.toString());
        // 重定向地址
        headers.add("Location", "http://localhost:8080/mypage.html");

        return ResponseEntity.status(HttpStatus.FOUND)
                .headers(headers)
                .build();
    }

    // 方法1：返回 RedirectView 对象并设置 Cookie
    @GetMapping("/redirect-with-cookie")
    public RedirectView redirectWithCookie(HttpServletResponse response) {
        // 创建 Cookie
        Cookie cookie = new Cookie("exampleCookie", "cookieValue");
        cookie.setDomain("父域名");
        cookie.setMaxAge(3600); // 设置过期时间（秒）
        cookie.setPath("/");    // 设置 Cookie 路径
        response.addCookie(cookie); // 添加到响应

        return new RedirectView("/mypage.html"); // 指向 static/mypage.html
    }
}
