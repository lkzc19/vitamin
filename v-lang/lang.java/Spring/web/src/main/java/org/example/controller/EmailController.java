package org.example.controller;

import org.example.anno.EmailRequired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    @EmailRequired("abc@123.xyz")
    @GetMapping("/email1/")
    public String getEmail() {
        return "abc@123.xyz";
    }
}
