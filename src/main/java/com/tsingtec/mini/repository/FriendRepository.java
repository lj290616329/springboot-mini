package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.websocket.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Integer>, JpaSpecificationExecutor<Friend> {
    Friend findByUidAndMode(Integer uid, String type);
}