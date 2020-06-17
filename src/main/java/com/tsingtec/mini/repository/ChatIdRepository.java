package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.websocket.ChatId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatIdRepository extends JpaRepository<ChatId, Integer>, JpaSpecificationExecutor<ChatId> {

    ChatId findByIds(String ids);

    @Query(value = "select id from socket_chat_id where ids like :ids",nativeQuery = true)
    List<Integer>  getIdByIdsLike(String ids);

    /**
     * 根据用户id 获取所以的朋友id
     * @param uid
     * @return
     */
    @Query(value = "select replace(replace(ids,:uid,''),'#','') ids from socket_chat_id where ids like :prem",nativeQuery = true)
    List<Integer> getFidsByUid(String uid, String prem);

}