package org.example.http.okhttp.interceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class RetryInterceptor implements Interceptor {

    public int maxRetry;    // 最大重试次数
    private int retry = 0;   // 假如设置为3次重试的话，则最大可能请求4次（默认1次+3次重试）

    public RetryInterceptor(int maxRetry) {
        this.maxRetry = maxRetry;
    }

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        while (!response.isSuccessful() && retry < maxRetry) {
            response.close();
            retry++;
            response = chain.proceed(request);
        }
        return response;
    }
}
