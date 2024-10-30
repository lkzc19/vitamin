package org.example.util;

import org.example.anno.Foo;
import org.springframework.web.method.HandlerMethod;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class RUtils {

    public List<String> getParamsName(HandlerMethod handlerMethod) {
        // 利用java反射获取参数
        Parameter[] parameters = handlerMethod.getMethod().getParameters();
        List<String> list = new ArrayList<>();
        for (Parameter parameter : parameters) {
            //判断这个参数是否被加入了 ParamsNotNull. 的注解
            //.isAnnotationPresent() 判断是否加了这个注释
            if(parameter.isAnnotationPresent(Foo.class)){
                list.add(parameter.getName());
            }
        }
        return list;
    }
}
