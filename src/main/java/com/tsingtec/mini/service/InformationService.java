package com.tsingtec.mini.service;

import com.tsingtec.mini.entity.mp.Information;
import com.tsingtec.mini.entity.mp.MpUser;
import com.tsingtec.mini.vo.req.mini.information.InformationReqVO;
import com.tsingtec.mini.vo.resp.app.mini.InformationRespVO;

import java.util.List;

public interface InformationService {

    void save(MpUser user, InformationReqVO vo);

    InformationRespVO findByUid(Integer uid);

    Boolean ifAuth(Integer uid);

    List<Information> getAll();


    Information getOne(Integer id);
}
