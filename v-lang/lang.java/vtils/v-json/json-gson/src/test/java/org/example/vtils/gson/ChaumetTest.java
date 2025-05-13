package org.example.vtils.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;

public class ChaumetTest {

    @Test
    @SneakyThrows
    public void test() {
        BufferedWriter PageView = new BufferedWriter(new FileWriter("/Users/lkzc19/Projects/startorch/g_chaumet/kafka_event/PageView.log"));
        BufferedWriter ProductDetailView = new BufferedWriter(new FileWriter("/Users/lkzc19/Projects/startorch/g_chaumet/kafka_event/ProductDetailView.log"));
        BufferedWriter AddToCart = new BufferedWriter(new FileWriter("/Users/lkzc19/Projects/startorch/g_chaumet/kafka_event/AddToCart.log"));
        BufferedWriter SubmitOrder = new BufferedWriter(new FileWriter("/Users/lkzc19/Projects/startorch/g_chaumet/kafka_event/SubmitOrder.log"));
        BufferedWriter OrderPaid = new BufferedWriter(new FileWriter("/Users/lkzc19/Projects/startorch/g_chaumet/kafka_event/OrderPaid.log"));
        BufferedWriter Register = new BufferedWriter(new FileWriter("/Users/lkzc19/Projects/startorch/g_chaumet/kafka_event/Register.log"));
        BufferedReader reader = new BufferedReader(new FileReader("/Users/lkzc19/Projects/startorch/g_chaumet/kafka_event/event.log"));

        String line;
        while ((line = reader.readLine()) != null) {
            JsonObject obj = JsonUtils.json2Object(line).getAsJsonObject();

            JsonElement event = obj.get("event");
            if (event == null) {
                continue;
            }

            switch (event.getAsString()) {
                case "PageView":
                    PageView.write(obj + "\n");
                    break;
                case "ProductDetailView":
                    ProductDetailView.write(obj + "\n");
                    break;
                case "AddToCart":
                    AddToCart.write(obj + "\n");
                    break;
                case "SubmitOrder":
                    SubmitOrder.write(obj + "\n");
                    break;
                case "OrderPaid":
                    OrderPaid.write(obj + "\n");
                    break;
                case "Register":
                    Register.write(obj + "\n");
                    break;
            }
        }
        reader.close();
    }

    @Test
    @SneakyThrows
    public void test1() {
        BufferedReader reader = new BufferedReader(new FileReader("/Users/lkzc19/dev/customer_data/chaumet/input/event_0512.log"));

        int all_count = 0;
        int event_count = 0;
        int production_count = 0;

        int adclick = 0;
        int blackbox = 0;
        int mpvistitor = 0;
        int oafans = 0;

        Map<String, Integer> UTM = new HashMap<>();

        String line;
        while ((line = reader.readLine()) != null) {
            all_count++;
            JsonObject obj = JsonUtils.json2Object(line).getAsJsonObject();

            JsonElement projectJe = obj.get("project");
            if (projectJe == null) {
                continue;
            }
            event_count++;
            String project = projectJe.getAsString();
            if (!"production".equals(project)) {
                continue;
            }
            production_count++;

            long time = obj.get("time").getAsLong();
            if (time <= 1746979200) {
                continue;
            }

            JsonObject properties = obj.get("properties").getAsJsonObject();
            JsonElement platformNameJe = properties.get("platform_name");
            if (platformNameJe == null) {
                continue;
            }
            String platformName = platformNameJe.getAsString();
            if (!"EC MNP".equals(platformName)) {
                continue;
            }

            String event = obj.get("event").getAsString();
            if (!"ProductDetailView".equals(event)) {
                continue;
            }

            JsonElement utmContentJe = properties.get("utm_content");
            if (utmContentJe == null) {
                continue;
            }
            String utmContent = utmContentJe.getAsString();

            Integer value = UTM.getOrDefault(utmContent, 0);
            UTM.put(utmContent, value + 1);

            if (utmContent.contains("adclick")) {
                adclick++;
            } else if (utmContent.contains("blackbox")) {
                blackbox++;
            } else if (utmContent.contains("mpvisitor")) {
                mpvistitor++;
            } else if (utmContent.contains("oafans")) {
                oafans++;
            }
        }
        reader.close();

        System.out.println("all_count\t\t\t" + all_count);
        System.out.println("event_count\t\t\t" + event_count);
        System.out.println("production_count\t" + production_count);

        System.out.println("================");

        System.out.println("adclick\t\t\t\t" + adclick);
        System.out.println("blackbox\t\t\t" + blackbox);
        System.out.println("mpvistitor\t\t\t" + mpvistitor);
        System.out.println("oafans\t\t\t\t" + oafans);

        System.out.println("================");

        UTM.forEach((k, v) -> System.out.println(k + "\t\t\t\t\t" + v));
    }
}
