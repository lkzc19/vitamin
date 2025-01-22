package org.example.config;


import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class YamlProcessorTest {

    @Test
    public void testLoad() {
        Map<String, Object> context = new HashMap<>();
        YamlProcessor.load(context);
        System.out.println(context);
    }
}