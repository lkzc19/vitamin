package com.example;

import com.example.model.OrmFoo;
import org.junit.Test;
import org.teasoft.bee.osql.api.Suid;
import org.teasoft.bee.osql.api.SuidRich;
import org.teasoft.honey.osql.core.HoneyConfig;
import org.teasoft.honey.osql.shortcut.BF;

import java.util.ArrayList;
import java.util.List;

public class MainTest {

    @Test
    public void testInsert() {
	    Suid suid= BF.getSuid();

        List<OrmFoo> ormFoos = new ArrayList<>();

        for (int i = 1; i <= 100000; i++) {
            OrmFoo ormFoo = new OrmFoo();
            ormFoo.setName("lkzc" + i);
            ormFoos.add(ormFoo);
        }
        long t1 = System.nanoTime() / 1000;
        for (OrmFoo ormFoo : ormFoos) {
            suid.insert(ormFoo);
        }
        long t2 = System.nanoTime() / 1000;
        System.out.println(t2 - t1);
    }

    @Test
    public void testBatchInsert() {
        SuidRich suid= BF.getSuidRich();

        List<OrmFoo> ormFoos = new ArrayList<>();

        for (int i = 1; i <= 100000; i++) {
            OrmFoo ormFoo = new OrmFoo();
            ormFoo.setName("lkzc" + i);
            ormFoos.add(ormFoo);
        }

        long t1 = System.nanoTime() / 1000;
        suid.insert(ormFoos);
        long t2 = System.nanoTime() / 1000;
        System.out.println(t2 - t1);
    }

    @Test
    public void testDelete() {
        HoneyConfig.getHoneyConfig().notDeleteWholeRecords = false;

        SuidRich suid= BF.getSuidRich();
        suid.delete(new OrmFoo());
    }
}
