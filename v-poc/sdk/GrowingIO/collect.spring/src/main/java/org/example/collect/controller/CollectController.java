package org.example.collect.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CollectController {

    @RequestMapping("/**")
    public void collect() {
        System.out.println("ok...");
    }
}
