package com.tsingtec.mini.service;

import com.tsingtec.mini.entity.mini.Doctor;
import com.tsingtec.mini.vo.req.mini.doctor.DoctorReqVO;
import com.tsingtec.mini.vo.req.mini.doctor.DoctorSetAdminReqVO;
import com.tsingtec.mini.vo.req.other.SwitchReqVO;
import com.tsingtec.mini.vo.resp.app.doctor.DoctorRespVO;

import java.util.List;

public interface DoctorService {

    Doctor findByAid(Integer aid);

    Doctor findById(Integer id);

    Boolean ifByUid(Integer uid);

    DoctorRespVO findByUid(Integer uid);

    void updateByUid(Integer uid, DoctorReqVO vo);

    void add(DoctorReqVO doctorReqVO);

    List<Doctor> findList();

    void updateById(Integer id, DoctorReqVO vo);

    void status(SwitchReqVO vo);

    void setAdmin(DoctorSetAdminReqVO vo);
}
