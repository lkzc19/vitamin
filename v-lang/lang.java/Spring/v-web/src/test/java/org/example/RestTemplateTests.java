package org.example;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class RestTemplateTests {

    @Resource
    private RestTemplate restTemplate;

    @Test
    public void test() {
        String forObject = restTemplate.getForObject("get", String.class);
        System.out.println(forObject);
    }

    @Test
    public void post() {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("appKey", "e3932b3fe5051c2c");
        requestBody.put("sign", "NjNmMzUzOTViYTFlYTI2NmU0OTcyNTRiN2IzMjBlNTkxNWMwNzVhZWNkZjEzMDc0Njg0Mzc1M2MwZTdhYWRkYg==");
        requestBody.put("worksheetId", "project");
        requestBody.put("pageSize", "50");
        requestBody.put("pageIndex", "1");
        requestBody.put("listType", "0");
        requestBody.put("notGetTotal", "false");
        requestBody.put("useControlId", "false");
        requestBody.put("getSystemControl", "false");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, String>> formEntity = new HttpEntity<>(requestBody, headers);
        System.out.println(restTemplate.postForObject("/post", formEntity, String.class));
    }
}
