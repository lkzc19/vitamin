package org.example.collect.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonUtils{

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public <T> T  toObject(String json, Class<T> clazz) {
        return objectMapper.readValue(json, clazz);
    }

    @SneakyThrows
    public <T> T toObject(String json, TypeReference<T> typeRef) {
        return objectMapper.readValue(json, typeRef);
    }

    @SneakyThrows
    public String toJson(Object obj) {
        return objectMapper.writeValueAsString(obj);
    }
}
