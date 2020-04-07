package com.tsingtec.mini.vo.req.material;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author lj
 * @Date 2020/4/5 22:53
 * @Version 1.0
 */
@Data
public class MaterialPageReqVO {

    @ApiModelProperty(value = "上级目录")
    private Integer pid = 0;//上级目录

    @ApiModelProperty(value = "第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "分页数量")
    private int pageSize=10;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;
}
