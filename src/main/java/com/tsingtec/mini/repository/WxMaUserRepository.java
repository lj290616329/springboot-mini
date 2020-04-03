package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.app.WxMaUser;
import com.tsingtec.mini.entity.sys.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface WxMaUserRepository extends JpaRepository<WxMaUser, Integer>, JpaSpecificationExecutor<WxMaUser> {
    WxMaUser findByOpenId(String openid);
}
