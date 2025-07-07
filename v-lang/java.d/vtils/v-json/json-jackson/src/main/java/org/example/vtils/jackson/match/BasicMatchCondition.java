package org.example.vtils.jackson.match;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.vtils.jackson.JsonUtils;

import java.util.List;

public class BasicMatchCondition implements MatchCondition {
    private final String field;
    private final JsonNode value;
    private final String operator;

    public BasicMatchCondition(String field, JsonNode value) {
        this(field, value, "$eq");
    }

    public BasicMatchCondition(String field, JsonNode value, String operator) {
        this.field = field;
        this.value = value;
        this.operator = operator;
    }

    @Override
    public boolean match(JsonNode data) {
        JsonNode node = JsonUtils.getNode(data, field);

        switch (operator) {
            case "$eq":
                return value.equals(node);
            case "$ne":
                return !value.equals(node);
            case "$gt":
                return compareNumbers(node, value) > 0;
            case "$gte":
                return compareNumbers(node, value) >= 0;
            case "$lt":
                return compareNumbers(node, value) < 0;
            case "$lte":
                return compareNumbers(node, value) <= 0;
            case "$in":
                return ((List<?>) value).contains(node);
            case "$nin":
                return !((List<?>) value).contains(node);
            default:
                return false;
        }
    }

    private int compareNumbers(JsonNode n1, JsonNode n2) {
        if (n1.isNumber() && n2.isNumber()) {
            return Double.compare(n1.asDouble(), n2.asDouble());
        }
        return 0;
    }
}
