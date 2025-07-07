package org.example.anno;

public @interface EmailRequired {
    String value() default "";
}
