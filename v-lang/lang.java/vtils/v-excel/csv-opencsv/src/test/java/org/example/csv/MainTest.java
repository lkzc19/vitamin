package org.example.csv;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainTest {

    @Test
    public void test() throws Exception {

        Set<String> jinjiang_3w = get_3w("/Users/lkzc19/dev/customer_data/jinjiang/jinjiang_3w.csv");
        System.out.println("jinjiang_3w: " + jinjiang_3w.size());

        Set<String> sensorsdata_3w = get_3w("/Users/lkzc19/dev/customer_data/jinjiang/sensorsdata_3w.csv");
        System.out.println("sensorsdata_3w: " + sensorsdata_3w.size());

        Set<String> webhook_3w = get_3w("/Users/lkzc19/dev/customer_data/jinjiang/webhook_3w.csv");
        System.out.println("webhook_3w: " + webhook_3w.size());

        Set<String> webhook_3w_id = get_3w("/Users/lkzc19/dev/customer_data/jinjiang/webhook_3w_id.csv");
        System.out.println("webhook_3w_id: " + webhook_3w_id.size());

        System.out.println("===============");

        for (String id : webhook_3w) {
            if (!jinjiang_3w.contains(id)) {
                System.out.println(id);
            }
        }
    }

    private Set<String> get_3w(String file) throws Exception {
        Set<String> set = new HashSet<>();

        CSVReader reader = new CSVReader(new FileReader(file));
        List<String[]> rows = reader.readAll();  // 读取所有行
        for (String[] row : rows) {
            set.addAll(Arrays.asList(row));
        }

        return set;
    }

    @Test
    public void test2() throws Exception {
        Integer upload = null;
        System.out.println(new Integer(1).equals(upload));
    }
}
