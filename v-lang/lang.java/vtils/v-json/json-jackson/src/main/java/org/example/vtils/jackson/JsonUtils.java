package org.example.vtils.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonUtils {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    public <T> T json2Object(String json, Class<T> clazz) {
        return objectMapper.readValue(json, clazz);
    }

    @SneakyThrows
    public <T> T json2Object(String json, TypeReference<T> typeRef) {
        return objectMapper.readValue(json, typeRef);
    }

    @SneakyThrows
    public JsonNode json2Object(String json) {
        return objectMapper.readTree(json);
    }

    @SneakyThrows
    public String object2Json(Object obj) {
        return objectMapper.writeValueAsString(obj);
    }

    @SneakyThrows
    public ObjectNode newObjectNode() {
        return objectMapper.createObjectNode();
    }

    @SneakyThrows
    public ArrayNode newArrayNode() {
        return objectMapper.createArrayNode();
    }
}
