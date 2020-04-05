package com.tsingtec.mini.service;

import com.tsingtec.mini.entity.mini.MaUser;
import com.tsingtec.mini.vo.req.app.mini.WxUserPageReqVO;
import org.springframework.data.domain.Page;

public interface MaUserService {

    MaUser findByOpenId(String openId);

    MaUser get(Integer id);

    Page<MaUser> pageInfo(WxUserPageReqVO wxUserReqVO);

    MaUser insert(MaUser wxUser);

    void update(MaUser wxUser);
}
