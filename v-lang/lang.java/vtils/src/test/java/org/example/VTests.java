package org.example;

import org.junit.Test;

public class VTests {

    @Test
    public void test() {
        retry(3);
    }

    private void retry(int times) {
        try {
            print(times);
        } catch (Exception e) {
            if (times > 0) {
                retry(times - 1);
            } else {
                throw e;
            }
        }
    }

    private void print(int times) {
        System.out.println("hello " + times);
        int i = 1 / 0;
    }
}
