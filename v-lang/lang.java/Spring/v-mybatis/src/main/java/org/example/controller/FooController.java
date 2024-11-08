package org.example.controller;

import jakarta.annotation.Resource;
import org.example.mapper.FooMapper;
import org.example.model.Foo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FooController {

    @Resource
    private FooMapper fooMapper;

    @PostMapping("/test")
    public void insert() {
        Foo foo = Foo.builder()
                .name("lkzc19")
                .build();
        fooMapper.insert(foo);
    }
}
