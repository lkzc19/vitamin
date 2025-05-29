package org.example.vtils.jackson.matcher;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.example.vtils.jackson.JsonUtils;
import org.example.vtils.jackson.match.MatchBuilder;
import org.example.vtils.jackson.match.MatchCondition;
import org.junit.Test;

public class MatchConditionTest {

    @Test
    public void mainTest() {
        ObjectNode data = JsonUtils.newObjectNode();
        ObjectNode properties = JsonUtils.newObjectNode();
        properties.put("name", "lkzc19");
        properties.put("age", 18);
        data.set("properties", properties);
        data.put("project", "default");

        ObjectNode c1 = JsonUtils.newObjectNode();
        c1.put("properties.name", "lkzc19");
        ArrayNode c1temp1 = JsonUtils.newArrayNode();
        ObjectNode c1temp2 = JsonUtils.newObjectNode();
        c1temp2.put("properties.age", 19);
        ObjectNode c1temp3 = JsonUtils.newObjectNode();
        c1temp3.put("properties.age", 17);
        c1temp1.add(c1temp3);
        c1temp1.add(c1temp2);
        c1.set("$or", c1temp1);

        MatchCondition m1 = MatchBuilder.build(c1);
        System.out.println(data);
        System.out.println(c1);
        System.out.println(m1.match(data));
    }
}