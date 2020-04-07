package com.tsingtec.mini.service;

import com.tsingtec.mini.entity.mp.MpUser;
import com.tsingtec.mini.vo.req.mini.WxUserPageReqVO;
import org.springframework.data.domain.Page;

public interface MpUserService {

    MpUser findByOpenId(String openId);

    MpUser findByUnionId(String unionId);

    MpUser get(Integer id);

    Page<MpUser> pageInfo(WxUserPageReqVO wxUserReqVO);

    MpUser save(MpUser wxUser);
}
