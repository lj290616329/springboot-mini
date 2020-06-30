package com.tsingtec.mini.entity.news;

import com.tsingtec.mini.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author lj
 * @Date 2020/6/23 21:33
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "news_tag")
public class ArticleTag extends BaseEntity {
    private String name;
    private Boolean status=true;
    private Integer sort;
}
