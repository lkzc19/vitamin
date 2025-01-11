package org.example.http.httpclient;

import org.example.http.httpclient4.HttpUtils;
import org.junit.Test;

public class HttpUtilsTests {

    @Test
    public void testGet() throws Exception {
        System.out.println(HttpUtils.send("https://httpbin.org/get"));
    }
}
