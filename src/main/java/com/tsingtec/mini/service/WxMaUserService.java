package com.tsingtec.mini.service;

import com.tsingtec.mini.entity.app.WxMaUser;
import com.tsingtec.mini.vo.req.app.mini.WxUserPageReqVO;
import org.springframework.data.domain.Page;

public interface WxMaUserService {

    WxMaUser findByOpenId(String openId);

    WxMaUser get(Integer id);

    Page<WxMaUser> pageInfo(WxUserPageReqVO wxUserReqVO);

    WxMaUser insert(WxMaUser wxUser);

    void update(WxMaUser wxUser);
}
