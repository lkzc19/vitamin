package org.example.config;

import lombok.experimental.UtilityClass;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@UtilityClass
public class YamlProcessor {
    // yaml文件可能的拓展名
    private static final List<String> EXT_YAML = Arrays.asList(".yaml", ".yml");
    // 约定配置文件名称
    private static final String APPLICATION = "application";
    private static final String APPLICATION_ENV = "application-%s";
    // 预置配置参数
    private static final String KEY_ENV = "env";

    /**
     * 最多支持两层yaml
     */
    public static void load(Map<String, Object> context) {
        Yaml yaml = new Yaml();
        ClassLoader classLoader = YamlProcessor.class.getClassLoader();

        // 加载application.yml
        Map<String, Object> source = load(yaml, getInputStreams(classLoader, APPLICATION, null));
        context.putAll(getFlattenedMap(source));

        // 加载application-${env}.yml
        String env = (String) context.get(KEY_ENV);
        if (isNotEmpty(env)) {
            Map<String, Object> sourceEnv = load(yaml, getInputStreams(classLoader, APPLICATION_ENV, env));
            if (isNotEmpty(sourceEnv)) {
                context.putAll(getFlattenedMap(sourceEnv));
            }
        }
    }

    private static Map<String, Object> load(Yaml yaml, List<InputStream> inputStreams) {
        for (InputStream inputStream : inputStreams) {
            if (inputStream == null) {
                continue;
            }
            return yaml.load(inputStream);
        }
        return Collections.emptyMap();
    }

    private static List<InputStream> getInputStreams(ClassLoader classLoader, String application, String env) {
        return EXT_YAML.stream().map(it -> {
            String resource;
            if (application.contains("-%s")) {
                resource = String.format(application + it, env);
            } else {
                resource = application + it;
            }
            return classLoader.getResourceAsStream(resource);
        }).collect(Collectors.toList());
    }

    /**
     * 获取无嵌套Map的Map
     */
    private static Map<String, Object> getFlattenedMap(Map<String, Object> source) {
        Map<String, Object> result = new LinkedHashMap<>();
        buildFlattenedMap(result, source, null);
        return result;
    }

    /**
     * 递归打平key，构建无嵌套Map的Map
     */
    private static void buildFlattenedMap(Map<String, Object> result, Map<String, Object> source, String path) {
        source.forEach((key, value) -> {
            // 将key打平
            if (isNotEmpty(path)) {
                // 目前只做普通map类型的解析
                key = path + "." + key;
            }
            // put
            if (value instanceof String) { // 字符串
                result.put(key, value);
            } else if (value instanceof Map) { // map类型打平
                // 去掉强转警告
                @SuppressWarnings("unchecked")
                Map<String, Object> map = (Map<String, Object>) value;
                buildFlattenedMap(result, map, key);
            } else { // 如数字，布尔
                result.put(key, (value != null ? value : ""));
            }
        });
    }

    private static boolean isNotEmpty(String str) {
        return str != null && !str.isEmpty();
    }

    private static boolean isNotEmpty(Map<?, ?> map) {
        return map != null && !map.isEmpty();
    }
}
