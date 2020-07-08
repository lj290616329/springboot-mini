package com.tsingtec.mini.service;

import com.tsingtec.mini.entity.mp.MpUser;
import com.tsingtec.mini.entity.websocket.Friend;
import com.tsingtec.mini.vo.req.websocket.SignReqVO;
import com.tsingtec.mini.vo.resp.websocket.FriendRespVO;
import com.tsingtec.mini.vo.resp.websocket.MineRespVO;
import com.tsingtec.mini.vo.resp.websocket.ToRespVO;

import java.util.List;

public interface FriendService {
    Friend checkByUidAndMode(Integer uid, String mode);
    MineRespVO getByUidAndMode(MpUser mpUser, String mode);
    List<FriendRespVO> getByUid(Integer uid);
    void update(SignReqVO signReqVO);
    ToRespVO get(Integer id);
}
