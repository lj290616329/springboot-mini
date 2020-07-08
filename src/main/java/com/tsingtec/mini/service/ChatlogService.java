package com.tsingtec.mini.service;

import com.tsingtec.mini.entity.websocket.Chatlog;
import com.tsingtec.mini.vo.resp.websocket.ChatlogRespVO;

import java.util.List;
import java.util.Map;

public interface ChatlogService {

    void save(Chatlog chatlog);

    void update(Chatlog chatlog);

    /**
     * 获取未接收到的消息
     * @param toids 发送的对象集合
     * @param status 状态
     * @return
     */
    List<Chatlog> findByToidInAndStatus(List<Integer> toids, Boolean status);

    /**
     * 获取自己为收到的消息
     * 此方法用于手机端
     * @param fromid
     * @param toid
     * @param status
     * @return
     */
    List<Chatlog> findByFromidAndToidAndStatus(Integer fromid, Integer toid, Boolean status);

    /**
     * 根据对话id获取聊天记录
     * @param chatid
     * @return
     */
    List<ChatlogRespVO> getChatLogByChatid(Integer uid, Integer chatid);


    Map<String,List<ChatlogRespVO>> findByChatids(Integer fromid, List<Integer> chatids);


    Map<String,List<ChatlogRespVO>> findByChatidInLimit(Integer fromid, List<Integer> chatids);

}
