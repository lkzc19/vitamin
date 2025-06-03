package org.example.vtils.gson;

import com.google.gson.JsonElement;
import lombok.SneakyThrows;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileTest {

    @Test
    @SneakyThrows
    public void test() {
        BufferedReader reader = new BufferedReader(new FileReader("../data/test01.log"));
        String line;
        while ((line = reader.readLine()) != null) {
            JsonElement jsonElement = JsonUtils.json2Object(line);
            jsonElement.getAsJsonArray().forEach(it -> {
                if (it.isJsonObject()) {
                    it.getAsJsonObject().entrySet().forEach(entry -> System.out.printf(entry.getKey() + " -> " + entry.getValue() + "\n"));
                } else {
                    System.out.println(it);
                }
            });
        }
    }
}
