package org.example.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class JacksonUtilsTests {

    @Test
    public void object2Json() {
        Map<String, Object> map = new HashMap<>();
        map.put("project", "default");
        System.out.println(JacksonUtils.object2Json(map));
    }

    @Test
    public void Json2Object() {
        String json = "{\"project\":\"default\"}";
        System.out.println(JacksonUtils.json2Object(json, new TypeReference<Map<String, Object>>() {}));
    }
}
