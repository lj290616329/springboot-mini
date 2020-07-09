package com.tsingtec.mini.vo.resp.websocket;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

/**
 * @Author lj
 * @Date 2020/6/6 18:35
 * @Version 1.0
 */
@Data
public class ChatlogRespVO {


    private String  username;//昵称

    private String avatar;//头像

    @JsonProperty(value = "id")
    private Integer fid;//friend id

    private String type = "friend";//类型

    private String content;;//内容

    private Date timestamp;//时间戳

    private Boolean mine = false;//是否自己

    @JsonIgnore
    private Integer fromid;

    @JsonIgnore
    private Integer toid;//接收人id

    public Integer getFid(){
        if(mine){
            return toid;
        }else {
            return fromid;
        }
    }
}
