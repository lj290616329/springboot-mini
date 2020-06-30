package com.tsingtec.mini.entity.notice;

import com.tsingtec.mini.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author lj
 * @Date 2020/6/24 15:52
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "wx_notice")
public class Notice extends BaseEntity {
    private String title;//通知标题
    private String des;//通知介绍
    private String content;//通知内容
    private Integer type;//调查表~~~
    private String qids;//问题id集合

}
