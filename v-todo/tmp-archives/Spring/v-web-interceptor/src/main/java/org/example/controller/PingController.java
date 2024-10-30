package org.example.controller;

import org.example.anno.Foo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @Foo
    @GetMapping("/foo")
    public String foo() {
        return "foo";
    }

    @GetMapping("/bar")
    public String bar() {
        return "bar";
    }
}
