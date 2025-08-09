package org.example;

import cn.idev.excel.FastExcel;
import org.junit.Test;

public class ExcelDemoTest {

    @Test
    public void simpleWrite() {
        String fileName = "simpleWrite-" + System.currentTimeMillis() + ".xlsx";

        FastExcel.write(fileName, DemoData.class)
                .sheet("模板")
                .doWrite(DemoData::data);
    }
}
