package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;

public class MainTests {

    @Test
    public void test() throws JsonProcessingException {

        String str = "{\n" +
                "\t\"foo\": \"f\",\n" +
                "    \"bar\": \"b\",\n" +
                "    \"world\": {\n" +
                "     \t\"w\": 1,\n" +
                "        \"o\": 2,\n" +
                "        \"r\": 3,\n" +
                "        \"l\": 4,\n" +
                "        \"d\": 5\n" +
                "                \n" +
                "    }\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode record = (ObjectNode) mapper.readTree(str);

        String foo = record.get("foo").asText();
        System.out.println(foo);

        ObjectNode world = (ObjectNode) record.get("world");
        world.put("hello", "world");

        System.out.println(record);

        ObjectNode word = (ObjectNode) record.get("word");
        System.out.println(word);
    }
}