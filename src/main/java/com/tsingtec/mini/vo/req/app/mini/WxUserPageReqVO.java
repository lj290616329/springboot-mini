package com.tsingtec.mini.vo.req.app.mini;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class WxUserPageReqVO {

    @ApiModelProperty(value = "第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "分页数量")
    private int pageSize=10;

    @ApiModelProperty(value = "用户昵称")
    private String nickName;

    @ApiModelProperty(value = "用户性别")
    private String gender;

    @ApiModelProperty(value = "用户手机号码")
    private String phone;

    @ApiModelProperty(value = "用户姓名")
    private String name;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;
}
