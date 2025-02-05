package org.example.vtils.httpclient4;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Test;

import java.util.Map;

public class MainTest {

    CloseableHttpClient httpclient = HttpClients.createDefault();

    @Test
    public void test() throws Exception {
        HttpGet httpGet = new HttpGet("https://httpbin.org/get");

//        try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
//            System.out.println(response.getStatusLine());
//            HttpEntity entity = response.getEntity();
//            EntityUtils.consume(entity);
//        }

//        Map<String, Object> result = httpclient.execute(httpGet, new CustomResponseHandler<>());
//        result.forEach((k, v) -> System.out.println(k + "=" + v));

        HttpbinModel result = httpclient.execute(httpGet, new CustomResponseHandler<HttpbinModel>());
        System.out.println(result);
    }
}
