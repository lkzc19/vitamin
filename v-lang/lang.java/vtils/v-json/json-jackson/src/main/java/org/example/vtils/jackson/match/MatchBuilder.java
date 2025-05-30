package org.example.vtils.jackson.match;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class MatchBuilder {

    public static MatchCondition build(JsonNode matchCondition) {
        List<MatchCondition> conditions = new ArrayList<>();

        matchCondition.fields().forEachRemaining(entry -> {
            String key = entry.getKey();
            JsonNode value = entry.getValue();

            if ("$and".equals(key) || "$or".equals(key) || "$nor".equals(key)) {
                List<MatchCondition> subConditions = new ArrayList<>();
                if (value.isArray()) {
                    ArrayNode arrayNode = (ArrayNode) value;
                    arrayNode.forEach(node -> subConditions.add(build(node)));
                } else {
                    subConditions.add(build(value));
                }
                conditions.add(new LogicalMatchCondition(subConditions, key));
            } else if (value.isObject()) {
                // 处理操作符 {$gt: 10} 等
                value.fields().forEachRemaining(it -> {
                    String operator = it.getValue().fieldNames().next();
                    JsonNode v = it.getValue().get(operator);
                    conditions.add(new BasicMatchCondition(it.getKey(), v, operator));
                });
            } else {
                // 简单相等条件 {name: "Alice"}
                conditions.add(new BasicMatchCondition(key, value));
            }
        });

        return conditions.size() == 1 ? conditions.get(0) : new LogicalMatchCondition(conditions, "$and");
    }
}
