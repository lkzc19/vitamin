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
    private final static String DOT = "\\.";

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

    public static JsonNode getNode(JsonNode node, String path) {
        if (node == null || path == null || path.isEmpty()) {
            return null;
        }
        String[] keys = path.split(DOT);
        JsonNode currentNode = node;
        for (String key : keys) {
            if (currentNode.has(key)) {
                currentNode = currentNode.get(key);
            } else {
                return null;
            }
        }
        return currentNode;
    }
}
