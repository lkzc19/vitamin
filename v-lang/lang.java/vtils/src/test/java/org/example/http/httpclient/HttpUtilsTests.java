package org.example.http.httpclient;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.example.http.httpclient4.HttpUtils;
import org.junit.Test;

import java.net.URI;

public class HttpUtilsTests {

    @Test
    public void testGet() throws Exception {
        System.out.println(HttpUtils.send("https://httpbin.org/get"));
    }

    @Test
    public void testPost() throws Exception {
        HttpPost post = new HttpPost("https://httpbin.org/post");
        post.addHeader("Content-Type", "application/json;charset=UTF-8");
    }
}
