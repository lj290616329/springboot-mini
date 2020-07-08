package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.websocket.Chatlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatLogRepository extends JpaRepository<Chatlog, Integer>, JpaSpecificationExecutor<Chatlog> {

    Chatlog getDistinctFirstByChatidAndStatusOrderByIdDesc(Integer toid,Boolean status);

    Integer countByChatidAndFromidAndStatus(Integer chatid,Integer fromid,Boolean status);

    List<Chatlog> findByToidInAndStatus(List<Integer> toids, Boolean status);

    List<Chatlog> findByStatus(Boolean status);

    List<Chatlog> findByFromidAndToidAndStatus(Integer fromid, Integer toid, Boolean status);


    @Query(value = "SELECT e.* FROM socket_chat_log e WHERE ( SELECT count(DISTINCT em.id) FROM socket_chat_log em WHERE em.id > e.id AND em.chatid = e.chatid and e.status=1 and em.status=1) < 20 and e.chatid in(:chatids) and e.status=1",nativeQuery = true)
    List<Chatlog> findByChatidInLimit(List<Integer> chatids);

    List<Chatlog> findByChatidIn(List<Integer> chatids);

    List<Chatlog> findByChatidInAndStatus(List<Integer> chatids, Boolean status);


    List<Chatlog> getChatlogByChatidOrderByCreateTimeDesc(Integer chatid);

    List<Chatlog> findByChatidAndStatusOrderByCreateTimeDesc(Integer chatid, Boolean status);
}
