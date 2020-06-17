package com.tsingtec.mini.service;

import com.tsingtec.mini.entity.mini.Doctor;
import com.tsingtec.mini.entity.mp.MpUser;

public interface DoctorService {

    Doctor findByMpUser(MpUser mpUser);
    Doctor findByAid(Integer aid);
}
