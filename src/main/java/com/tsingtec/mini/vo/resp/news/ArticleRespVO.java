package com.tsingtec.mini.vo.resp.news;

import lombok.Data;

import java.util.Date;

/**
 * @Author lj
 * @Date 2020/6/12 11:55
 * @Version 1.0
 */
@Data
public class ArticleRespVO {

    private String title; //标题

    private Boolean ifShowTitle = true;//是否显示标题，默认1

    private String pic;//封面图

    private Boolean ifShowPic = true;

    private String description;//简介

    private String content;//内容

    private String tags; //标签类型

    private Integer id;//id

    private Date updateTime;//最后登录时间
}
