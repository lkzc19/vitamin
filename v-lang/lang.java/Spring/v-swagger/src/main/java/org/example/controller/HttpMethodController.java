package org.example.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "http 方法")
@RestController
public class HttpMethodController {

    @GetMapping("/get")
    public void get() {

    }

    @PostMapping("/post")
    public void post() {

    }

    @PutMapping("/put")
    public void put() {

    }

    @DeleteMapping("/delete")
    public void delete() {

    }

    @PatchMapping("/patch")
    public void patch() {

    }
}
