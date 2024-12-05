package org.example;

import org.junit.Test;

import java.util.List;

public class ClassUtilsTest {

    @Test
    public void testGetClasses() {
        List<Class<?>> classes = ClassUtils.getClasses(
                "org.example.json",
                Thread.currentThread().getContextClassLoader()
        );
        for (Class<?> cls : classes) {
            System.out.println(cls.getName());
        }
    }
}