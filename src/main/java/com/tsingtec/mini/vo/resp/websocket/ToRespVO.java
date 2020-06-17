package com.tsingtec.mini.vo.resp.websocket;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author lj
 * @Date 2020/6/6 17:08
 * @Version 1.0
 */
@Data
public class ToRespVO {

    private Integer id;//用户id

    @JsonProperty("username")
    private String name;//用户名

    private String avatar;//头像

    private String sign;//签名

    private String type="friend";

    private String status="online"; //在线状态 online：在线、hide：隐身
}
