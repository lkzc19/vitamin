package org.example.vtils.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.SneakyThrows;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class EventTest {

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
}
