package com.tsingtec.mini.vo.resp.websocket;

import lombok.Data;

import java.util.List;

/**
 * @Author lj
 * @Date 2020/6/6 17:10
 * @Version 1.0
 */
@Data
public class FriendRespVO {
    private String groupname="我的病人";
    private Integer id=1;
    private List<MineRespVO> list;//好友列表
}
