package org.example.json;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FastjsonUtils {

    public <T> T json2Object(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    public <T> T json2Object(String json, TypeReference<T> typeRef) {
        return JSON.parseObject(json, typeRef);
    }

    public String object2Json(Object obj) {
        return JSON.toJSONString(obj);
    }
}
