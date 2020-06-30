package com.tsingtec.mini.entity.mini;

import com.tsingtec.mini.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author lj
 * @Date 2020/6/29 16:49
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "mini_subscribe_message")
public class SubscribeMessage extends BaseEntity {
    private Integer uid;//用户id
    private String tmpIds;//订阅消息id
}
