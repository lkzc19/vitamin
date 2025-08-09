package org.example.x;

import cn.idev.excel.FastExcel;
import cn.idev.excel.annotation.ExcelProperty;
import cn.idev.excel.read.listener.PageReadListener;
import lombok.Data;
import org.junit.Test;

import java.util.*;

public class XyTest {

    @Test
    public void mainTest() throws Exception {
//        Set<String> list = read1("/Users/lkzc19/Projects/startorch/g_xingye/data/ebank_mobile#data_collect#prod.xlsx");
        Set<String> list = read2("/Users/lkzc19/Projects/startorch/g_xingye/data/属性比较结果.xlsx");
        list.forEach(System.out::println);
        System.out.println(String.join(",", list));
    }

    @Test
    public void diff() {
        Set<String> oldU = read3("/Users/lkzc19/Projects/startorch/g_xingye/data/acreditcard.b15092.c24101.d24163.click.Click_old.csv");
        Set<String> newU = read3("/Users/lkzc19/Projects/startorch/g_xingye/data/acreditcard.b15092.c24101.d24163.click.Click_new.csv");

        System.out.println(oldU.size());
        System.out.println(newU.size());

        Set<String> old_new = new HashSet<>();
        for (String u : oldU) {
            if (!newU.contains(u)) {
                old_new.add(u);
            }
        }
        System.out.println(old_new.size());

        Set<String> new_old = new HashSet<>();
        for (String u : newU) {
            if (!oldU.contains(u)) {
                new_old.add(u);
            }
        }
        System.out.println(new_old.size());
    }

    private Set<String> read3(String file) {
        Set<String> list = new LinkedHashSet<>();

        FastExcel.read(file, UMarkXlsx.class, new PageReadListener<UMarkXlsx>(dataList -> {
            for (UMarkXlsx data : dataList) {
                if (data.u_mark == null) {
                    continue;
                }
                list.add(data.u_mark);
            }
        })).sheet(2).doRead();

        return list;
    }

    private Set<String> read2(String file) {
        Set<String> list = new LinkedHashSet<>();

        FastExcel.read(file, PropXlsx.class, new PageReadListener<PropXlsx>(dataList -> {
            for (PropXlsx data : dataList) {
                if (data.prop == null) {
                    continue;
                }
                list.add(data.prop);
            }
        })).sheet(2).doRead();

        System.out.println(list.size());
        return list;
    }

    private Set<String> read1(String file) {
        Set<String> list = new TreeSet<>();

        FastExcel.read(file, CollectionPlan.class, new PageReadListener<CollectionPlan>(dataList -> {
            for (CollectionPlan data : dataList) {
                if (data.prop == null) {
                    continue;
                }
                if ("org.example.vtils.fastexcel.x.XyTest".equals(data.prop)) {
                    continue;
                }
                list.add(data.prop);
            }
        })).sheet(2).doRead();

        return list;
    }

    @Data
    public static class CollectionPlan {
        @ExcelProperty(index = 3)
        private String prop;
    }

    @Data
    public static class PropXlsx {
        @ExcelProperty(index = 0)
        private String prop;
    }

    @Data
    public static class UMarkXlsx {
        @ExcelProperty(index = 0)
        private String u_mark;
    }
}
