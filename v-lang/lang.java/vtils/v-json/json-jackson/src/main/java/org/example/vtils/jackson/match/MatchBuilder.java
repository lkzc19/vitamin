package org.example.vtils.jackson.match;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class MatchBuilder {

    public static MatchCondition build(JsonNode matchCondition) {
        List<MatchCondition> conditions = new ArrayList<>();

        matchCondition.fields().forEachRemaining(entry -> {
            String key = entry.getKey().toLowerCase();

            if ("$and".equals(key) || "$or".equals(key) || "$nor".equals(key)) {
                List<MatchCondition> subConditions = new ArrayList<>();
                subConditions.add(build(entry.getValue()));
                conditions.add(new LogicalMatchCondition(subConditions, key));
            } else if (entry.getValue().isObject()) {
                // 处理操作符 {$gt: 10} 等
                entry.getValue().fields().forEachRemaining(it -> {
                    String operator = it.getValue().fieldNames().next();
                    JsonNode value = it.getValue().get(operator);
                    conditions.add(new BasicMatchCondition(it.getKey(), value, operator));
                });
            } else {
                // 简单相等条件 {name: "Alice"}
                conditions.add(new BasicMatchCondition(key, entry.getValue()));
            }
        });

        return conditions.size() == 1 ? conditions.get(0) : new LogicalMatchCondition(conditions, "$and");
    }
}
