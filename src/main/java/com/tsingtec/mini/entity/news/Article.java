package com.tsingtec.mini.entity.news;

import com.tsingtec.mini.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author lj
 * @Date 2020/6/10 15:28
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "news_article")
public class Article extends BaseEntity {

    private Integer aid;//admin表id

    private String title; //标题

    private Boolean ifShowTitle = false;//是否显示标题，默认1

    private String pic;//封面图

    private Boolean ifShowPic = false;

    private String description;//简介

    private String content;//内容

    private Integer tag; //标签类型

    private Integer sort;//排序

    private Integer hits=0;//点击量

    private Boolean isTop=false;//是否置顶 每个分类只有一个是置顶的
}
