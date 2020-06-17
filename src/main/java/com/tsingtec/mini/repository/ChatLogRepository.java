package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.websocket.Chatlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatLogRepository extends JpaRepository<Chatlog, Integer>, JpaSpecificationExecutor<Chatlog> {


    List<Chatlog> findByToidInAndStatus(List<Integer> toids, Boolean status);

    List<Chatlog> findByStatus(Boolean status);

    List<Chatlog> findByFromidAndToidAndStatus(Integer fromid, Integer toid, Boolean status);

    List<Chatlog> findByChatidIn(List<Integer> chatids);

    List<Chatlog> findByChatidInAndStatus(List<Integer> chatids, Boolean status);


    List<Chatlog> getChatlogByChatidOrderByCreateTimeDesc(Integer chatid);

    List<Chatlog> findByChatidAndStatusOrderByCreateTimeDesc(Integer chatid, Boolean status);
}
