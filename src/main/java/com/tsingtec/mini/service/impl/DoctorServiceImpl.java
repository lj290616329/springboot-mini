package com.tsingtec.mini.service.impl;

import com.tsingtec.mini.entity.mini.Doctor;
import com.tsingtec.mini.exception.BusinessException;
import com.tsingtec.mini.exception.code.BaseExceptionType;
import com.tsingtec.mini.repository.DoctorRepository;
import com.tsingtec.mini.service.DoctorService;
import com.tsingtec.mini.utils.BeanMapper;
import com.tsingtec.mini.utils.BeanUtil;
import com.tsingtec.mini.vo.req.mini.doctor.DoctorReqVO;
import com.tsingtec.mini.vo.resp.app.doctor.DoctorRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author lj
 * @Date 2020/3/29 13:54
 * @Version 1.0
 */
@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;



    @Override
    public Doctor findByAid(Integer aid) {
        return doctorRepository.findByAid(aid);
    }

    @Override
    public Doctor findById(Integer id) {
        return doctorRepository.getOne(id);
    }

    @Override
    public DoctorRespVO findByUid(Integer uid) {
        Doctor doctor = doctorRepository.findByMpUser_Id(uid);
        if(null==doctor){
            throw new BusinessException(BaseExceptionType.MINI_ERROR,"不存在的医生信息");
        }
        return BeanMapper.map(doctor,DoctorRespVO.class);
    }


    @Override
    public void update(Integer uid, DoctorReqVO vo) {
        Doctor doctor = doctorRepository.findByMpUser_Id(uid);
        if(null==doctor){
            throw new BusinessException(BaseExceptionType.MINI_ERROR,"不存在的医生信息");
        }
        BeanUtil.copyPropertiesIgnoreNull(vo,doctor);
        doctorRepository.save(doctor);
    }
}
