package org.example.x.bayer;

import cn.idev.excel.FastExcel;
import cn.idev.excel.read.listener.PageReadListener;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.example.vtils.jackson.JsonUtils;
import org.junit.Test;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class BayerTest {

    @Test
    public void mainTest() throws Exception {
        Map<String, String> map = new HashMap<>();
        map.put("/Users/lkzc19/Projects/startorch/g_bayer/vx_data/19年导出用户数据明细.xlsx", "/Users/lkzc19/Projects/startorch/g_bayer/vx_data/profile_19.log");
        map.put("/Users/lkzc19/Projects/startorch/g_bayer/vx_data/20年导出用户数据明细.xlsx", "/Users/lkzc19/Projects/startorch/g_bayer/vx_data/profile_20.log");
        map.put("/Users/lkzc19/Projects/startorch/g_bayer/vx_data/21年导出用户数据明细.xlsx", "/Users/lkzc19/Projects/startorch/g_bayer/vx_data/profile_21.log");
        map.put("/Users/lkzc19/Projects/startorch/g_bayer/vx_data/22年导出用户数据明细.xlsx", "/Users/lkzc19/Projects/startorch/g_bayer/vx_data/profile_22.log");
        map.put("/Users/lkzc19/Projects/startorch/g_bayer/vx_data/23年导出用户数据明细.xlsx", "/Users/lkzc19/Projects/startorch/g_bayer/vx_data/profile_23.log");
        map.put("/Users/lkzc19/Projects/startorch/g_bayer/vx_data/24年导出用户数据明细.xlsx", "/Users/lkzc19/Projects/startorch/g_bayer/vx_data/profile_24.log");
        map.put("/Users/lkzc19/Projects/startorch/g_bayer/vx_data/25年导出用户数据明细.xlsx", "/Users/lkzc19/Projects/startorch/g_bayer/vx_data/profile_25.log");


        for (Map.Entry<String, String> entry : map.entrySet()) {
            List<ObjectNode> list = read(entry.getKey());

            BufferedWriter writer = new BufferedWriter(new FileWriter(entry.getValue()));
            for (ObjectNode it : list) {
                writer.write(it.toString() + "\n");
            }
            writer.close();
        }
    }

    private List<ObjectNode> read(String file) {
        List<ObjectNode> list = new ArrayList<>();

        FastExcel.read(file, VxMpModel.class, new PageReadListener<VxMpModel>(dataList -> {
            for (VxMpModel data : dataList) {
                ObjectNode profile = JsonUtils.newObjectNode();
                profile.put("distinct_id", data.getUnionid());
                profile.put("anonymous_id", data.getUnionid());
                profile.put("type", "profile_set");
                profile.put("project", "default");

                ObjectNode identities = JsonUtils.newObjectNode();
                identities.put("$identity_login_id", data.getUnionid());
                identities.put("$identity_mp_unionid", data.getUnionid());
                identities.put("$identity_mp_{" + data.getOpenid() +"}_openid", data.getOpenid());
                profile.set("identities", identities);

                ObjectNode properties = JsonUtils.newObjectNode();
                properties.put("is_follow_wechat", "Follow".equals(data.getStatus()));
                String wechat_follow_source = data.getFollowSource();
                if ("Account Name Card Share".equals(wechat_follow_source)) {
                    wechat_follow_source = "公众号名片分享";
                } else if ("Channels".equals(wechat_follow_source)) {
                    wechat_follow_source = "视频号";
                } else if ("Livestream".equals(wechat_follow_source)){
                    wechat_follow_source = "视频号直播";
                } else if ("Post Teaser Page".equals(wechat_follow_source)){
                    wechat_follow_source = "图文页内名称点击";
                } else if ("Reprint".equals(wechat_follow_source)){
                    wechat_follow_source = "他人转载";
                } else if ("Search".equals(wechat_follow_source)){
                    wechat_follow_source = "搜索公众号";
                } else if ("Wechat QR codes".equals(wechat_follow_source)){
                    wechat_follow_source = "扫描临时二维码";
                } else if ("Upper Right Menu in Post".equals(wechat_follow_source)){
                    wechat_follow_source = "图文页右上角菜单";
                } else if ("WeChat Ad".equals(wechat_follow_source)){
                    wechat_follow_source = "微信广告";
                } else if ("Other".equals(wechat_follow_source)){
                    wechat_follow_source = "其他";
                }

                properties.put("wechat_follow_source", wechat_follow_source);
                properties.put("wechat_follow_time", dateFormat(data.getFollowedDate()));
                properties.put("wechat_unfollow_time", dateFormat(data.getUnfollowedDate()));
                profile.set("properties", properties);

                list.add(profile);
            }
        })).sheet().doRead();

        return list;
    }

    private String dateFormat(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        return sdf.format(date);
    }
}
