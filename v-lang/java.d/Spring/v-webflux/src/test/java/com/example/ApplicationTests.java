package com.example;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest
class ApplicationTests {

    @Resource
    private WebClient webClient;

    @Test
    void contextLoads() {
    }

    @Test
    void test1() {
        String block = webClient.get()
                .uri("/get")
                .retrieve()
                .bodyToMono(String.class)
                .block();
        System.out.println(block);
    }

    @Test
    void test2() {
        ResponseEntity<String> block = webClient.get()
                .uri("/get")
                .retrieve()
                .toEntity(String.class)
                .block();
        System.out.println(block);
    }
}
