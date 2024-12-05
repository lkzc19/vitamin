package org.example;

import io.growing.sdk.java.GrowingAPI;
import io.growing.sdk.java.dto.GioCdpEventMessage;
import org.junit.Test;

import java.util.Arrays;

public class MainTest {

    @Test
    public void test() {
        GrowingAPI project = new GrowingAPI.Builder().setProjectKey("your accountId").setDataSourceId("your dataSourceId").build();

        GioCdpEventMessage eventMessage = new GioCdpEventMessage.Builder()
                .eventTime(System.currentTimeMillis())            // 默认为系统当前时间 (选填)
                .eventKey("3")                                    // 埋点事件标识 (必填)
                .eventNumValue(1.0)                               // 打点事件数值 (选填), 已废弃
                .anonymousId("device_id")                         // 访问用户ID (选填)
                .loginUserKey("account")                          // 登录用户KEY (选填，需有规划并在平台配置后再上报)
                .loginUserId("417abcabcabcbac")                   // 登陆用户ID (选填)
                .addEventVariable("product_name", "苹果")          // 事件属性 (选填)
                .addEventVariable("product_classify", "水果")      // 事件属性 (选填)
                .addEventVariable("product_price", 14)            // 事件属性 (选填)
                .addItem("item_id", "item_key")                   // 物品模型ID, KEY (选填)
                .build();

        // anonymousId 和 loginUserId 参数，不能同时为空
        GioCdpEventMessage msg = new GioCdpEventMessage.Builder()
                .eventTime(System.currentTimeMillis())            // 默认为系统当前时间 (选填)
                .eventKey("eventKey")                             // 事件标识 (必填)
                .domain("com.growingio.app")                      // App包名或H5域名（选填）
                .urlScheme("growing.123c12fb12f123cc")            // 链接协议（选填）
                .deviceBrand("google")                            // 设备品牌（选填）
                .deviceModel("Nexus 5")                           // 设备型号（选填）
                .deviceType("PHONE")                              // 设备类型（选填）
                .appVersion("1.2.4")                              // App版本（选填）
                .appName("看数助手")                               // App名称（选填）
                .language("zh_CN")                                // 语言（选填）
                .anonymousId("device_id")                         // 访问用户ID (选填)
                .loginUserKey("account")  // 登录用户KEY (选填，需有规划并在平台配置后再上报)
                .loginUserId("417abcabcabcbac")                   // 登录用户ID (选填)
                .addEventVariable("product_name", "cdp苹果")       // 事件属性 (选填)
                .addEventVariable("product_classify", Arrays.asList("苹果", "香蕉"))       // 事件属性 (选填)
//                .addEventVariables(map)                           // 事件属性集合 (选填)
                .addItem("itemId", "itemKey")                     // 物品模型ID, KEY (选填)
                .build();

        project.send(msg);
    }
}
