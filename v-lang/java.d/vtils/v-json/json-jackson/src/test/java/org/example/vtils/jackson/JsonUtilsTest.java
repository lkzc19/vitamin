package org.example.vtils.jackson;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtilsTest {

    @Test
    public void testToObject1() {
        String json = "{\n" +
                "  \"field1\": \"qwe\",\n" +
                "  \"field2\": 1,\n" +
                "  \"field3\": [\"as\", \"cs\", \"cs\"]\n" +
                "}";
        JsonModel model = JsonUtils.toObject(json, JsonModel.class);
        System.out.println(model);
    }

    @Test
    public void testTo() {
        String json = "{\n" +
                "  \"field1\": \"qwe\",\n" +
                "  \"field2\": 1,\n" +
                "  \"field3\": [\"as\", \"cs\", \"cs\"]\n" +
                "}";
        Map<String, Object> map = JsonUtils.toObject(json, new TypeReference<Map<String, Object>>() {});
        map.forEach((k, v) -> System.out.println(k + ":" + v));
    }

    @Test
    public void testToObject3() {
        String json = "{\n" +
                "  \"field1\": \"qwe\",\n" +
                "  \"field2\": 1,\n" +
                "  \"field3\": [\"as\", \"cs\", \"cs\"]\n" +
                "}";
        JsonNode jn = JsonUtils.toObject(json);
        ObjectNode on = (ObjectNode) jn;
        on.put("field4", "qwe");
        System.out.println(jn);
    }

    @Test
    public void testToJson() {
        List<String> field3 = new ArrayList<>();
        field3.add("as");
        field3.add("cs");
        JsonModel foo = new JsonModel("cs", 2, field3);
        String json = JsonUtils.toJson(foo);
        System.out.println(json);
    }
}