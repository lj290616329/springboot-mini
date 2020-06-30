package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.mp.Information;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface InformationRepository extends JpaRepository<Information, Integer>, JpaSpecificationExecutor<Information> {
    Information findByMpUser_Id(Integer uid);
}
