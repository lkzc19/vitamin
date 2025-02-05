package org.example.vtils.httpclient4;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class CustomResponseHandler<T> implements ResponseHandler<T> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public T handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        int status = response.getStatusLine().getStatusCode();
        if (status >= 200 && status < 300) {
            HttpEntity entity = response.getEntity();
            return entity != null ? objectMapper.readValue(entity.getContent(), new TypeReference<T>() {}) : null;
        } else {
            throw new ClientProtocolException("Unexpected response status: " + status);
        }
    }
}
