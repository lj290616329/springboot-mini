package com.tsingtec.mini.vo.req.news;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Author lj
 * @Date 2020/6/12 12:06
 * @Version 1.0
 */
@Data
public class ArticleUpdateReqVO {

    @ApiModelProperty(value = "文章id",name="id")
    @NotBlank(message = "文章id不能为空")
    private Integer id;

    @ApiModelProperty(value = "文章标题",name="title")
    @NotBlank(message = "文章标题不能为空")
    private String title; //标题

    private Boolean ifShowTitle = false;//是否显示标题，默认1

    @ApiModelProperty(value = "封面图",name="pic")
    @NotBlank(message = "封面图不能为空")
    private String pic;//封面图

    private Boolean ifShowPic = false;

    @ApiModelProperty(value = "文章简介",name="name")
    private String description;//简介

    @ApiModelProperty(value = "显示名称",name="name")
    @NotBlank(message = "显示名称不能为空")
    private String content;//内容

    @ApiModelProperty(value = "显示名称",name="name")
    @NotBlank(message = "显示名称不能为空")
    private String tags; //标签类型
}
