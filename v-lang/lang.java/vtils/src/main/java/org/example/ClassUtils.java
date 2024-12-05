package org.example;

import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class ClassUtils {

    public List<Class<?>> getClasses(String packageName, ClassLoader classLoader) {
        String path = packageName.replace('.', '/');
        List<Class<?>> classes = new ArrayList<>();

        // 获取类加载器的资源
        URL resource = classLoader.getResource(path);
        if (resource == null) {
            throw new IllegalArgumentException("Cannot find package " + packageName);
        }

        // 将路径转换为文件系统路径
        File directory = new File(resource.getFile());

        // 过滤出 .class 文件
        File[] files = directory.listFiles((dir, name) -> name.endsWith(".class"));

        if (files == null) {
            throw new IllegalArgumentException("Cannot find classes within package " + packageName);
        }

        for (File file : files) {
            String className = file.getName().substring(0, file.getName().length() - 6);
            try {
                Class<?> cls = Class.forName(packageName + '.' + className, false, classLoader);
                classes.add(cls);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return classes;
    }
}
