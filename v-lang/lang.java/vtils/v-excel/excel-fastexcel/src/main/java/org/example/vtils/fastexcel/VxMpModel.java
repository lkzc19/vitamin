package org.example.vtils.fastexcel;

import cn.idev.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class VxMpModel {
    @ExcelProperty("Unionid")
    private String unionid;

    @ExcelProperty("Openid")
    private String openid;

    @ExcelProperty("Follow source关注来源")
    private String followSource;

    @ExcelProperty("Status当前状态")
    private String status;

    @ExcelProperty("Followed Date 关注时间")
    private Date followedDate;

    @ExcelProperty("Unfollowed Date取关时间")
    private Date unfollowedDate;
}
