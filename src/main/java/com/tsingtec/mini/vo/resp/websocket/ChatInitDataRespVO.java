package com.tsingtec.mini.vo.resp.websocket;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author lj
 * @Date 2020/6/6 17:07
 * @Version 1.0
 */
@Data
public class ChatInitDataRespVO {
    private MineRespVO mine;
    private List<FriendRespVO> friend;
    private Map<String,List<ChatlogRespVO>> history;
    private ToRespVO to;
}
