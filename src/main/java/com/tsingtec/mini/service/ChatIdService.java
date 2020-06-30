package com.tsingtec.mini.service;

import java.util.List;

public interface ChatIdService {

    /**
     * 获取对话id
     * @param toid
     * @param fromid
     * @return
     */
    Integer getByToidAndFromId(Integer toid, Integer fromid);

    /**
     * 根据用户id获取所有的对话信息
     * @param uid
     * @return
     */
    List<Integer> getIdByIdsLike(Integer uid);
}
