package com.tsingtec.mini.entity.websocket;

import com.tsingtec.mini.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * @Author lj
 * 用户信息
 * @Date 2020/6/5 21:35
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "socket_friend")
public class Friend extends BaseEntity {
    private Integer uid;//用户id
    private String username;//用户名
    private String avatar;//头像
    private String sign;//签名
    private String type;//mobileOR pc

    @Transient
    private Integer unRead=0;

    @Transient
    private Chatlog chatlog;

}
