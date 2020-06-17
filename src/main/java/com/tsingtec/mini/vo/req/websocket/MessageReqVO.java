package com.tsingtec.mini.vo.req.websocket;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @Author lj
 * @Date 2020/6/6 12:42
 * @Version 1.0
 */
@Data
public class MessageReqVO {

    private String username;//昵称

    private String avatar;//头像

    private Integer chatid;//对话id

    private String type;//类型
    private String content;//内容

    private Integer fromid;//发送者

    private Integer toid;//接收者

    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss", timezone = "GMT+8")
    private Date timestamp = new Date();//时间戳

    @Override
    public String toString(){
        return "{" +
                "\"type\":\"chatMessage\"," +
                "\"data\":{" +
                    "\"username\":\""+username+"\"," +
                    "\"avatar\":\""+avatar+"\"," +
                    "\"id\":"+fromid+"," +
                    "\"type\":\""+type+"\"," +
                    "\"timestamp\":\""+timestamp+"\"," +
                    "\"content\":\""+content+"\"," +
                    "\"fromid\":"+fromid+"," +
                    "\"toid\":"+toid+
                    "}"+
                "}";
    }
}
