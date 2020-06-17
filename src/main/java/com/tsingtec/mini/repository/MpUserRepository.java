package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.mp.MpUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MpUserRepository extends JpaRepository<MpUser, Integer>, JpaSpecificationExecutor<MpUser> {

    MpUser findByOpenId(String openId);

    MpUser findByUnionId(String unionId);

    MpUser findByMiniOpenid(String miniOpenid);
}
