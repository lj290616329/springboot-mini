package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.sys.SysLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository
public interface SysLogRepository extends JpaRepository<SysLog, Integer>,JpaSpecificationExecutor<SysLog> {

    @Modifying
    @Transactional
    @Query("delete from SysLog s where s.id in (?1)")
    void deleteBatch(@Param(value = "ids")List<Integer> ids);
}
