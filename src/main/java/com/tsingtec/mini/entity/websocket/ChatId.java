package com.tsingtec.mini.entity.websocket;

import com.tsingtec.mini.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author lj
 * 对话id 前面的id 小于后面的id
 * @Date 2020/6/6 15:44
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "socket_chat_id")
public class ChatId extends BaseEntity {
    private  String ids;//#fid#fid   friendid
}