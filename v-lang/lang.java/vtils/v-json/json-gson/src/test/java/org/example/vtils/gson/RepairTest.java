package org.example.vtils.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public class RepairTest {

    @Test
    @SneakyThrows
    public void test() {
        HashSet<Integer> set = new HashSet<>();

        BufferedWriter writer = new BufferedWriter(new FileWriter("../data/output/repair.log"));
        BufferedReader reader = new BufferedReader(new FileReader("../data/input/kafka.log"));
        String line;
        while ((line = reader.readLine()) != null) {
            JsonObject obj = JsonUtils.json2Object(line).getAsJsonObject();

            String errorType = obj.get("error_type").getAsString();
            if (!"PROPERTY_WITH_WRONG_TYPE".equals(errorType)) {
                continue;
            }

            obj.remove("extractor");
            obj.remove("recv_time");
            obj.remove("ngx_ip");
            obj.remove("error_type");
            obj.remove("error_reason");
            obj.remove("process_time");
            obj.remove("_flush_time");
            obj.remove("otime");
            obj.remove("original_track_id");
            obj.remove("error_info");

            int trackId = (int)(Math.random() * 90000000) + 10000000;
            while (set.contains(trackId)) {
                trackId = (int)(Math.random() * 90000000) + 10000000;
            }
            set.add(trackId);

            obj.addProperty("_track_id", trackId);

            obj.addProperty("time_free", true);

            writer.write(obj + "\n");
        }
        reader.close();
        writer.close();
    }

    @Test
    public void random() {
        System.out.println((int)(Math.random() * 90000000) + 10000000);
    }

    @Test
    @SneakyThrows
    public void count() {
        HashMap<String, Integer> map = new HashMap<>();

        BufferedReader reader = new BufferedReader(new FileReader("../data/input/kafka.log"));
        String line;
        while ((line = reader.readLine()) != null) {
            JsonObject obj = JsonUtils.json2Object(line).getAsJsonObject();

            String errorType = obj.get("error_type").getAsString();
            map.put(errorType, map.getOrDefault(errorType, 0) + 1);
        }

        map.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    @Test
    @SneakyThrows
    public void reason() {
        HashMap<String, Integer> map = new HashMap<>();

        BufferedReader reader = new BufferedReader(new FileReader("../data/input/kafka.log"));
        String line;
        while ((line = reader.readLine()) != null) {
            JsonObject obj = JsonUtils.json2Object(line).getAsJsonObject();

            String error_type = obj.get("error_type").getAsString();
            if (!"PROPERTY_WITH_WRONG_TYPE".equals(error_type)) {
                continue;
            }

            String error_reason = obj.get("error_reason").getAsString();
            map.put(error_reason, map.getOrDefault(error_reason, 0) + 1);
        }

        map.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    @Test
    @SneakyThrows
    public void trackId() {
        HashMap<String, Integer> map = new HashMap<>();

        BufferedReader reader = new BufferedReader(new FileReader("../data/output/repair.log"));
        String line;
        while ((line = reader.readLine()) != null) {
            JsonObject obj = JsonUtils.json2Object(line).getAsJsonObject();

            String id = obj.get("_track_id").getAsString();
            map.put(id, map.getOrDefault(id, 0) + 1);
        }

        map.forEach((k, v) -> {
            if (v > 1) {
                System.out.println(k + ": " + v);
            }
        });
    }

    @Test
    @SneakyThrows
    public void get200() {
        int i = 100;
        BufferedWriter writer = new BufferedWriter(new FileWriter("../data/output/part100.log"));
        BufferedReader reader = new BufferedReader(new FileReader("../data/output/repair.log"));
        String line;
        while ((line = reader.readLine()) != null) {
            JsonObject obj = JsonUtils.json2Object(line).getAsJsonObject();

            if (i == 0) {
                break;
            }
            i--;
            writer.write(obj + "\n");
        }
        writer.close();
    }

    @Test
    @SneakyThrows
    public void project() {
        HashMap<String, Integer> map = new HashMap<>();

        BufferedReader reader = new BufferedReader(new FileReader("../data/output/repair.log"));
        String line;
        while ((line = reader.readLine()) != null) {
            JsonObject obj = JsonUtils.json2Object(line).getAsJsonObject();

            String id = obj.get("project").getAsString();
            map.put(id, map.getOrDefault(id, 0) + 1);
        }

        map.forEach((k, v) -> {
            System.out.println(k + ": " + v);
        });
    }
}
