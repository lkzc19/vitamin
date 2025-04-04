package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class LoginController {

    @GetMapping("/api/sso/login")
    public void login(String code) {
        log.info("code: {}", code);
    }
}
