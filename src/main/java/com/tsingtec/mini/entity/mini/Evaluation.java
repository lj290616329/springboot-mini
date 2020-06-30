package com.tsingtec.mini.entity.mini;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tsingtec.mini.entity.BaseEntity;
import com.tsingtec.mini.entity.mp.Information;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * @Author lj
 * @Date 2020/6/23 14:12
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "mini_evaluation")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Evaluation extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="uid",columnDefinition ="用户id")//指定外键名称
    private Information information;

    private Integer did;//医生id

    private String content;//测评内容

    private String result;//测评结果

}
