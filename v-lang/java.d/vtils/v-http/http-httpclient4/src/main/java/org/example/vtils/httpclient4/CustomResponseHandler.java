package org.example.vtils.httpclient4;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;

import java.io.IOException;

public class CustomResponseHandler<T> implements ResponseHandler<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Class<T> responseType; // 支持普通类型
    private final TypeReference<T> responseTypeRef; // 支持带有泛型的类型

    public CustomResponseHandler(Class<T> responseType) {
        this.responseType = responseType;
        this.responseTypeRef = null;
    }

    public CustomResponseHandler(TypeReference<T> responseTypeRef) {
        this.responseTypeRef = responseTypeRef;
        this.responseType = null;
    }

    @Override
    public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        int status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // 根据传入的类型信息反序列化
                if (responseType != null) {
                    return objectMapper.readValue(entity.getContent(), responseType);
                } else if (responseTypeRef != null) {
                    return objectMapper.readValue(entity.getContent(), responseTypeRef);
                } else {
                    throw new IllegalStateException("Neither responseType nor responseTypeRef is set.");
                }
            } else {
                return null;
            }
        } else {
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
    }
}
