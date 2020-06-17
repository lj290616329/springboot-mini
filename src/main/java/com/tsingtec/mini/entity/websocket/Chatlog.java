package com.tsingtec.mini.entity.websocket;

import com.tsingtec.mini.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @Author lj
 * 对话log
 * @Date 2020/6/5 21:35
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "socket_chat_log")
public class Chatlog extends BaseEntity {

    private Integer chatid;//friend id

    private String  username;//昵称

    private String avatar;//头像

    private Integer toid;//接收人id

    private String type = "friend";//类型

    private String content;;//内容

    private Integer fromid;//发送人id

    private Date timestamp;//时间戳

    private Boolean status = false;//是否发送

    @Transient
    private Boolean mine = false;//是否自己
}
