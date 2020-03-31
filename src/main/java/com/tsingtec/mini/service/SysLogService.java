package com.tsingtec.mini.service;

import com.tsingtec.mini.entity.sys.SysLog;
import com.tsingtec.mini.vo.req.sys.log.SysLogPageReqVO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface SysLogService {

    Page<SysLog> pageInfo(SysLogPageReqVO vo);

    void insert(SysLog sysLog);

    void delete(List<Integer> logIds);
}
