package org.example.vtils.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.experimental.UtilityClass;

@UtilityClass
public class JsonUtils {

    private final Gson gson = new Gson();

    public <T> T json2Object(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public <T> T json2Object(String json, TypeToken<T> typeToken) {
        return gson.fromJson(json, typeToken);
    }

    public JsonElement json2Object(String json) {
        return JsonParser.parseString(json);
    }

    public String object2Json(Object obj) {
        return gson.toJson(obj);
    }
}