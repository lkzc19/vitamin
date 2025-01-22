package org.example.config;

import org.junit.Test;

import java.io.IOException;

public class PropertiesUtilsTests {

    @Test
    public void test() throws IOException {
        PropertiesUtils.load("config.properties");
        System.out.println(PropertiesUtils.get("port"));
        System.out.println(PropertiesUtils.get("alarm.role"));
        System.out.println(PropertiesUtils.get("alarm.job"));
        System.out.println(PropertiesUtils.get("xxx"));
    }
}
