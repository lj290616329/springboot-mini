package com.tsingtec.mini.vo.req.news;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author lj
 * @Date 2020/6/12 11:53
 * @Version 1.0
 */
@Data
public class ArticlePageReqVO {

    private Integer aid;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "标签")
    private  Integer tag;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    @ApiModelProperty(value = "第几页")
    private int pageNum=1;

    @ApiModelProperty(value = "分页数量")
    private int pageSize=10;
}
