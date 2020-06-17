package com.tsingtec.mini.vo.resp.websocket;

import lombok.Data;

import java.util.Date;

/**
 * @Author lj
 * @Date 2020/6/6 18:35
 * @Version 1.0
 */
@Data
public class ChatlogRespVO {

    private Integer chatid;

    private String  username;//昵称

    private String avatar;//头像

    private Integer id;//接收人id

    private String type = "friend";//类型

    private String content;;//内容

    private Date timestamp;//时间戳

    private Boolean mine = false;//是否自己

}
