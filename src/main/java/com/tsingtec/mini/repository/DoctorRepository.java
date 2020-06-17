package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.mini.Doctor;
import com.tsingtec.mini.entity.mp.MpUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface DoctorRepository extends JpaRepository<Doctor, Integer>, JpaSpecificationExecutor<Doctor> {

    Doctor findByMpUser(MpUser mpUser);

    Doctor findByAid(Integer aid);
}
