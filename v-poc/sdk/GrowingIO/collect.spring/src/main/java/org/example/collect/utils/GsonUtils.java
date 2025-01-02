package org.example.collect.utils;

import com.google.gson.*;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

@UtilityClass
public class GsonUtils {

    private static final JsonParser JsonParser; //NOSONAR
    private static final GsonBuilder gsonBuilder;
    private static final Gson gson;

    static {
        JsonParser = new JsonParser(); //NOSONAR
        gsonBuilder = new GsonBuilder();
        gson = gsonBuilder
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setLongSerializationPolicy(LongSerializationPolicy.STRING)
                .create();
    }

    public static JsonObject parseObject(String json) throws JsonSyntaxException {
        if(getJSONType(json)){
            try {
                return JsonParser.parse(json).getAsJsonObject(); //NOSONAR
            }catch (Exception e){
                throw new JsonSyntaxException("parse error " + e.getMessage());
            }
        }
        throw new JsonSyntaxException( json + " is not json object");
    }

    public static JsonElement parse(String json) throws JsonSyntaxException {
        return JsonParser.parse(json); //NOSONAR
    }

    public static String toJson(Object obj) throws JsonSyntaxException {
        return gson.toJson(obj);
    }

    public static boolean getJSONType(String str) { //NOSONAR
        boolean result = false;
        if (StringUtils.hasText(str)) {
            str = str.trim();
            if ( (str.startsWith("{") && str.endsWith("}")) || (str.startsWith("[") && str.endsWith("]")) ) {
                result = true;
            }
        }
        return result;
    }
}
