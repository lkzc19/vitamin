package org.example.csv;

import com.opencsv.CSVReader;
import org.junit.Test;

import java.io.FileReader;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class XyTest {

    @Test
    public void diff() throws Exception {

        Set<String> oldU = get_umark("/Users/lkzc19/Projects/startorch/g_xingye/data/acreditcard.b15092.c24101.d24163.click.Click_old.csv");
        System.out.println(oldU.size());

        Set<String> newU = get_umark("/Users/lkzc19/Projects/startorch/g_xingye/data/acreditcard.b15092.c24101.d24163.click.Click_new.csv");
        System.out.println(newU.size());

        Set<String> old_new = new HashSet<>();
        for (String u : oldU) {
            if (!newU.contains(u)) {
                old_new.add(u);
            }
        }
        System.out.println(old_new.size());
        old_new.forEach(System.out::println);

        Set<String> new_old = new HashSet<>();
        for (String u : newU) {
            if (!oldU.contains(u)) {
                new_old.add(u);
            }
        }
        System.out.println(new_old.size());
        new_old.forEach(System.out::println);
    }

    private Set<String> get_umark(String file) throws Exception {
        Set<String> set = new HashSet<>();

        CSVReader reader = new CSVReader(new FileReader(file));
        reader.skip(1);
        List<String[]> rows = reader.readAll();  // 读取所有行
        for (String[] row : rows) {
            set.add(row[1]);
        }

        return set;
    }

    @Test
    public void test2() throws Exception {
        Integer upload = null;
        System.out.println(new Integer(1).equals(upload));
    }
}
