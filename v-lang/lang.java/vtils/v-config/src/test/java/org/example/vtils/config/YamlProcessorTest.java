package org.example.vtils.config;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class YamlProcessorTest {

    @Test
    public void testLoad() {
        Map<String, Object> map = new HashMap<>();
        YamlProcessor.load(map);
        map.forEach((k, v) -> System.out.println(k + "=" + v));
    }
}