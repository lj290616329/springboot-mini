package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.mini.SubscribeMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeMessageRepository extends JpaRepository<SubscribeMessage, Integer>, JpaSpecificationExecutor<SubscribeMessage> {
    SubscribeMessage findByUid(Integer uid);
    void deleteByUid(Integer uid);
}
