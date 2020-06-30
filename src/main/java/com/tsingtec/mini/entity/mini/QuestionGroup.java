package com.tsingtec.mini.entity.mini;

import com.tsingtec.mini.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author lj
 * @Date 2020/6/19 16:44
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "mini_question_group")
public class QuestionGroup extends BaseEntity {
    private String name;
    private Integer sort;
}
