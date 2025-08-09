package org.example.vtils.jackson.x;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.SneakyThrows;
import org.example.vtils.jackson.JsonUtils;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class XingyeTest {

    @SneakyThrows
    @Test
    public void test() {
        BufferedReader reader = new BufferedReader(new FileReader("data/ebank_mobile#event_prop#prod.json"));

        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        ArrayNode node = (ArrayNode) JsonUtils.toObject(sb.toString());
        Set<String> d = new HashSet<>();
        List<String> l = new ArrayList<>();
        for (JsonNode it : node) {
            if (!it.has("name")) {
                continue;
            }
            String name = it.get("name").asText().toLowerCase();
            boolean exist = d.add(name);
            if (!exist) {
                System.out.println(it.get("name").asText());
            }

            l.add(name);
        }

        System.out.println(l.size() - d.size());
    }
}
