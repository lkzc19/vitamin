package org.example.vtils.jackson.match;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.List;

public class LogicalMatchCondition implements MatchCondition {
    private final List<MatchCondition> matchConditions;
    private final String operator;

    public LogicalMatchCondition(List<MatchCondition> matchConditions, String operator) {
        this.matchConditions = matchConditions;
        this.operator = operator;
    }

    @Override
    public boolean match(JsonNode data) {
        if (matchConditions == null || matchConditions.isEmpty()) {
            return true;
        }

        if ("$and".equals(operator)) {
            return matchConditions.stream().allMatch(c -> c.match(data));
        } else if ("$or".equals(operator)) {
            return matchConditions.stream().anyMatch(c -> c.match(data));
        } else if ("$nor".equals(operator)) {
            return matchConditions.stream().noneMatch(c -> c.match(data));
        }

        return false;
    }
}
