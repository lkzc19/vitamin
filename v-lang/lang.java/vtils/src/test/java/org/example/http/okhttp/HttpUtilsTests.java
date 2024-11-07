package org.example.http.okhttp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.http.okhttp.interceptor.RetryInterceptor;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

public class HttpUtilsTests {

    private OkHttpClient client;

    @Before
    public void setUp() {
        client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(false)
                .addInterceptor(new RetryInterceptor(3))//重试
                .build();
    }

    @Test
    public void test() throws IOException {
        Request request = new Request.Builder()
                .url("http://localhost:3000/retry?retryId=lkzc18&retryCount=3")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println("onResponse:" + response.body().string());
    }
}
