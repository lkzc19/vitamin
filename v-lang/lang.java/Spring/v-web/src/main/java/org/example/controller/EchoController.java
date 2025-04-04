package org.example.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
