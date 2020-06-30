package com.tsingtec.mini.service;

import com.tsingtec.mini.entity.mini.Doctor;
import com.tsingtec.mini.vo.req.mini.doctor.DoctorReqVO;
import com.tsingtec.mini.vo.resp.app.doctor.DoctorRespVO;

public interface DoctorService {

    Doctor findByAid(Integer aid);

    Doctor findById(Integer id);

    DoctorRespVO findByUid(Integer uid);

    void update(Integer uid, DoctorReqVO vo);

}
