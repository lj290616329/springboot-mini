package com.tsingtec.mini.service;

import com.tsingtec.mini.entity.mp.MpUser;
import com.tsingtec.mini.entity.websocket.Friend;
import com.tsingtec.mini.vo.req.websocket.SignReqVO;
import com.tsingtec.mini.vo.resp.websocket.FriendRespVO;
import com.tsingtec.mini.vo.resp.websocket.MineRespVO;
import com.tsingtec.mini.vo.resp.websocket.ToRespVO;

import java.util.List;

public interface FriendService {
    Friend checkByUidAndType(Integer uid, String type);
    MineRespVO getByUidAndType(MpUser mpUser, String type);
    List<FriendRespVO> getByUid(Integer uid);
    void update(SignReqVO signReqVO);
    ToRespVO get(Integer id);
}
