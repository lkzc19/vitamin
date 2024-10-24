package org.example;

import org.junit.Test;

import java.io.File;

public class Md5UtilsTests {

    @Test
    public void testMd5() {
        File file = new File("./data/test.txt");
        System.out.println(Md5Utils.md5(file));
    }
}
