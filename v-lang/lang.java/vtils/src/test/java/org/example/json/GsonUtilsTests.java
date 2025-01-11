package org.example.json;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.Strictness;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import org.junit.Test;

import java.io.*;
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

    @Test
    public void testFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("data/json.log"));
        String line;
        while ((line = reader.readLine()) != null) {
            JsonElement je = GsonUtils.json2Object(line.trim());
//            System.out.println(JsonParser.parseString(je.getAsString()));

            if (je.isJsonArray()) {
                System.out.println("数组: " + je);
            } else {
                System.out.println("非数组: " + je);
            }
        }
    }

    @Test
    public void testFromFile1() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("data/json.log"));
        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(JsonParser.parseString(line));
            System.out.println(GsonUtils.json2Object(line));
        }
    }

    @Test
    public void testFromFile2() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("data/bad_json.log"));
        String line;
        while ((line = reader.readLine()) != null) {
            String s = GsonUtils.object2Json(GsonUtils.json2Object(line));
            JacksonUtils.json2Object(s).forEach(System.out::println);
        }
    }
}
