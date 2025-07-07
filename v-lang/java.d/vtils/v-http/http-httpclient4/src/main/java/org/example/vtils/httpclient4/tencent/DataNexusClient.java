package org.example.vtils.httpclient4.tencent;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class DataNexusClient {

    private final CloseableHttpClient httpclient = HttpClients.createDefault();

//    public String generateNonce() {
//        return UUID.randomUUID().toString().replace("-", "");
//    }
//
//    public String userActionAdd() throws IOException, URISyntaxException {
//        URIBuilder uriBuilder = new URIBuilder("https://api.e.qq.com/v1.3/user_actions/add");
//        uriBuilder
//                .setParameter("access_token", "value1")
//                .setParameter("timestamp", String.valueOf(System.currentTimeMillis() / 1000))
//                .setParameter("nonce", UUID.randomUUID().toString().replace("-", ""));
//        HttpPost httpPost = new HttpPost("https://api.e.qq.com/v1.3/user_actions/add");
//        CloseableHttpResponse execute = httpclient.execute(httpPost, );
//    }
}
