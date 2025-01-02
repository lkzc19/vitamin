package org.example.collect;

import com.github.os72.protobuf360.InvalidProtocolBufferException;
import com.google.gson.JsonObject;
import io.growing.collector.tunnel.protocol.EventDto;
import io.growing.collector.tunnel.protocol.EventList;
import org.example.collect.utils.GsonUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@SpringBootTest
class ApplicationTests {

    @Test
    void contextLoads() throws InvalidProtocolBufferException {
        String str = "CvgCEhBhY2ZlZjE1MWQ1MTRjNmY1GgkxODIxMjkwMDUonPi03MEyQgxvcmRlclN1Y2Nlc3NIAWokCg1vcmRlclRpbWVfdmFyEhMyMDI0LTEyLTMxIDE0OjUwOjIxahcKDXByb2R1Y3RJZF92YXISBjE3ODA4OGofCg9wcm9kdWN0VHlwZV92YXISDOmUpuaxn+WVhuWfjmoWChFwcm9kdWN0VHlwZUlEX3ZhchIBM2opCg9vcmRlck51bWJlcl92YXISFk8yNDEyMzExODYyNzgyMDY5NDYyMDVqSgoPcHJvZHVjdE5hbWVfdmFyEjfllYblk4HmoIfpopjllYblk4HmoIfpopjljYHkuIAg5a2X5ZWG5ZOB5Y2B5LiA5a2X5ZWG5ZOBahIKDXBheUFtb3VudF92YXISATBqGgoScGF5QW1vdW50VG90YWxfdmFyEgQyMjAwahAKCXBvaW50X3ZhchIDNzM0ggEQYjdlM2I3NmE1ZWQ1NDlmYQ==";
        byte[] decode = Base64.getDecoder().decode(str);
        EventList eventList = EventList.parseFrom(decode);
        List<EventDto> valuesList = eventList.getValuesList();

        for (EventDto eventDto : valuesList) {
            String json = GsonUtils.toJson(eventDto);
            JsonObject jsonObject = GsonUtils.parseObject(json);
            JsonObject newJsonObject = new JsonObject();

            jsonObject.entrySet().forEach(entry -> {
                String key = toCamelCase(entry.getKey());
                if ("attributes".equals(key)) {
                    JsonObject map_data = entry.getValue().getAsJsonObject().getAsJsonObject("map_data");
                    JsonObject attributes = new JsonObject();
                    map_data.entrySet().forEach(entry2 -> {
                        attributes.add(entry2.getKey(), entry2.getValue());
                    });
                    newJsonObject.add(key, attributes);
                } else {
                    newJsonObject.add(key, entry.getValue());
                }
            });

            System.out.println(newJsonObject);
        }
    }

    public static String toCamelCase(String input) {
        // 按下划线分割字符串
        String[] parts = input.split("_");
        StringBuilder camelCase = new StringBuilder(parts[0].toLowerCase());

        // 将剩余的部分转换为驼峰命名
        for (int i = 1; i < parts.length; i++) {
            camelCase.append(parts[i].substring(0, 1).toUpperCase()).append(parts[i].substring(1).toLowerCase());
        }

        return camelCase.toString();
    }

    @Test
    public void test() {
        System.out.println(toCamelCase("text_Value_"));
    }

    @Test
    public void test1() throws Exception {
        String str = "CvQCEhBhY2ZlZjE1MWQ1MTRjNmY1GgkxODIxMjg0Njkoq+6qtsEyQgxvcmRlclN1Y2Nlc3NIAWokCg1vcmRlclRpbWVfdmFyEhMyMDI0LTEyLTMwIDE2OjU5OjI5ahcKDXByb2R1Y3RJZF92YXISBjE3ODA2NGofCg9wcm9kdWN0VHlwZV92YXISDOmUpuaxn+WVhuWfjmoWChFwcm9kdWN0VHlwZUlEX3ZhchIBM2opCg9vcmRlck51bWJlcl92YXISFk8yNDEyMzAxODU0OTE2ODUzODIxOTBqQwoPcHJvZHVjdE5hbWVfdmFyEjDlrp7nianllYblk4HigJTmtYvor5XkuIDlj7fppoblrp7nianjgJDljIXpgq7jgJFqFQoNcGF5QW1vdW50X3ZhchIEOTgwMGoaChJwYXlBbW91bnRUb3RhbF92YXISBDk5MDBqEAoJcG9pbnRfdmFyEgM1MDCCARBiN2UzYjc2YTVlZDU0OWZh";
        byte[] decode = Base64.getDecoder().decode(str);

        String filePath = "/Users/lkzc19/dev/pb.log"; // 目标文件路径
        Files.write(Paths.get(filePath), decode);
    }
}
