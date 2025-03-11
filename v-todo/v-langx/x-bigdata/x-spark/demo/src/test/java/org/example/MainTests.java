package org.example;

import org.example.source.FileSourceTests;
import org.junit.Test;

import java.util.logging.Logger;

public class MainTests {

    private final Logger logger = Logger.getLogger(MainTests.class.getName());

    @Test
    public void log() {
        logger.info("Hello World");
    }
}