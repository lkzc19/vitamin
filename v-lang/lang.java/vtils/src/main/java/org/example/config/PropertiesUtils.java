package org.example.config;

import lombok.experimental.UtilityClass;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@UtilityClass
public class PropertiesUtils {

    private static final Properties prop = new Properties();

    public static void load(String filename) throws IOException {
        InputStream inputStream = PropertiesUtils.class.getClassLoader().getResourceAsStream(filename);
        prop.load(inputStream);
    }

    public static String get(String name) {
        return prop.getProperty(name);
    }
}
