package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.mini.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer>, JpaSpecificationExecutor<Doctor> {

    Doctor findByMpUser_Id(Integer uid);

    Doctor findByAid(Integer aid);
}
