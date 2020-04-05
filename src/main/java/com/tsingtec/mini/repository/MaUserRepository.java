package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.mini.MaUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MaUserRepository extends JpaRepository<MaUser, Integer>, JpaSpecificationExecutor<MaUser> {

    MaUser findByOpenId(String openid);

    MaUser findByUnionId(String unionid);
}
