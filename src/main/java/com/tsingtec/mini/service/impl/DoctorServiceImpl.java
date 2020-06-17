package com.tsingtec.mini.service.impl;

import com.tsingtec.mini.entity.mini.Doctor;
import com.tsingtec.mini.entity.mp.MpUser;
import com.tsingtec.mini.repository.DoctorRepository;
import com.tsingtec.mini.service.DoctorService;
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
    public Doctor findByMpUser(MpUser mpUser) {
        return doctorRepository.findByMpUser(mpUser);
    }

    @Override
    public Doctor findByAid(Integer aid) {
        return doctorRepository.findByAid(aid);
    }
}
