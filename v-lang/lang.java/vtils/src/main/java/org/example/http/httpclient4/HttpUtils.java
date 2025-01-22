package org.example.http.httpclient4;


import lombok.experimental.UtilityClass;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;

@UtilityClass
public class HttpUtils {

    private static CloseableHttpClient client ;

    private static final Integer TIME_OUT = 30000;

    public static CloseableHttpClient getClient() {
        if(client == null) {
            // 构造对应的客户端
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(TIME_OUT)
                    .setSocketTimeout(TIME_OUT)
                    .setConnectionRequestTimeout(TIME_OUT)
                    .build();
            client = HttpClients.custom()
                    .setDefaultRequestConfig(requestConfig)
                    .build();
        }
        return client ;
    }

    public static CloseableHttpResponse send(String url, String requestBody) throws IOException {
        HttpPost post = new HttpPost(url);
        post.addHeader("Content-Type", "application/json;charset=UTF-8");
        post.setEntity(new StringEntity(requestBody, "utf-8"));
        return getClient().execute(post);
    }

    public static CloseableHttpResponse send(String url) throws IOException {
        HttpGet get = new HttpGet(url);
        return getClient().execute(get);
    }
}
