package com.tsingtec.mini.vo.resp.websocket;

import com.tsingtec.mini.vo.req.websocket.MessageReqVO;
import lombok.Data;

/**
 * @Author lj
 * @Date 2020/6/10 11:50
 * @Version 1.0
 */
@Data
public class ChatMsgRespVO {
    private String type;
    private MessageReqVO data;
}
