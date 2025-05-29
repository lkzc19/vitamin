package org.example.vtils.jackson.match;

import com.fasterxml.jackson.databind.JsonNode;

public interface MatchCondition {
    boolean match(JsonNode data);
}
