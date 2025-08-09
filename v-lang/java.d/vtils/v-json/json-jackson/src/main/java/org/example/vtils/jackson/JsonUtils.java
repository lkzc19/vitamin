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

    private final static ObjectMapper _MAPPER = new ObjectMapper();
    private final static String DOT = "\\.";

    @SneakyThrows
    public static <T> T toObject(String json, Class<T> clazz) {
        return _MAPPER.readValue(json, clazz);
    }

    @SneakyThrows
    public static <T> T toObject(String json, TypeReference<T> typeRef) {
        return _MAPPER.readValue(json, typeRef);
    }

    @SneakyThrows
    public static JsonNode toObject(String json) {
        return _MAPPER.readTree(json);
    }

    @SneakyThrows
    public static String toJson(Object obj) {
        return _MAPPER.writeValueAsString(obj);
    }

    @SneakyThrows
    public static ObjectNode newObjectNode() {
        return _MAPPER.createObjectNode();
    }

    @SneakyThrows
    public static ArrayNode newArrayNode() {
        return _MAPPER.createArrayNode();
    }

    @SneakyThrows
    public static ArrayNode newValueNode(Object value) {
        return _MAPPER.valueToTree(value);
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
