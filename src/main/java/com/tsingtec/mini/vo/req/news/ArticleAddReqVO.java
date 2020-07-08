package com.tsingtec.mini.vo.req.news;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Author lj
 * @Date 2020/6/12 11:58
 * @Version 1.0
 */
@Data
public class ArticleAddReqVO {

    private Integer aid;//admin表id

    @ApiModelProperty(value = "文章标题",name="title")
    @NotBlank(message = "文章标题不能为空")
    private String title; //标题

    private Boolean ifShowTitle = false;//是否显示标题，默认1

    @ApiModelProperty(value = "封面图",name="pic")
    @NotBlank(message = "封面图不能为空")
    private String pic;//封面图

    private Boolean ifShowPic = false;

    @ApiModelProperty(value = "简介",name="description")
    private String description;//简介

    @ApiModelProperty(value = "文章内容",name="content")
    @NotBlank(message = "文章内容不能为空")
    private String content;//内容

    @ApiModelProperty(value = "文章类型",name="tag")
    @NotNull(message = "请选择文章类型")
    private Integer tag; //标签类型
}
