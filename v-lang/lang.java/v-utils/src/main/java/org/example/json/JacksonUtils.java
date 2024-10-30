package org.example.json;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JacksonUtils {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public <T> T  json2Object(String json, Class<T> clazz) {
        return objectMapper.readValue(json, clazz);
    }

    @SneakyThrows
    public <T> T json2Object(String json, TypeReference<T> typeRef) {
        return objectMapper.readValue(json, typeRef);
    }

    @SneakyThrows
    public String object2Json(Object obj) {
        return objectMapper.writeValueAsString(obj);
    }
}
