package org.example.json;

import com.google.gson.reflect.TypeToken;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GsonUtilsTests {

    @Test
    public void testJson2Object1() {
        String json = "{\n" +
                "  \"field1\": \"qwe\",\n" +
                "  \"field2\": 1,\n" +
                "  \"field3\": [\"as\", \"cs\", \"cs\"]\n" +
                "}";
        TestJsonModel model = GsonUtils.json2Object(json, TestJsonModel.class);
        System.out.println(model);
    }

    @Test
    public void testJson2Object2() {
        String json = "{\n" +
                "  \"field1\": \"qwe\",\n" +
                "  \"field2\": 1,\n" +
                "  \"field3\": [\"as\", \"cs\", \"cs\"]\n" +
                "}";
        Map<String, Object> map = GsonUtils.json2Object(json, new TypeToken<Map<String, Object>>() {});
        map.forEach((k, v) -> System.out.println(k + ":" + v));
    }

    @Test
    public void testObject2Json() {
        List<String> field3 = new ArrayList<>();
        field3.add("as");
        field3.add("cs");
        TestJsonModel foo = new TestJsonModel("cs", 2, field3);
        String json = GsonUtils.object2Json(foo);
        System.out.println(json);
    }
}
