package com.example.vtils.file;

import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;

public class MainTest {

    @Test
    public void test() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader("data/id.txt"));

        HashMap<String, Integer> map = new HashMap<>();
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.isEmpty()) {
                continue;
            }
            map.put(line, map.getOrDefault(line, 0) + 1);
        }
        map.entrySet().stream().filter(it -> it.getValue() == 1).forEach(System.out::println);
    }
}
