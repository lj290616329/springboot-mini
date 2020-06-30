package com.tsingtec.mini.entity.mini;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tsingtec.mini.entity.BaseEntity;
import com.tsingtec.mini.entity.mp.MpUser;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @Author lj
 * @Date 2020/6/11 17:10
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "mini_doctor")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Doctor extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="uid",columnDefinition ="用户id")//指定外键名称
    private MpUser mpUser;//用户id

    @Column(unique = true)
    private Integer aid;//绑定的登录账号id
    private String name;//姓名
    private String phone;//电话
    private String goodAt;//擅长
    private String description;//简介
    private String introduce;//具体介绍

}
