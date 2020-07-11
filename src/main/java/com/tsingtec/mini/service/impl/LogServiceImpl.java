package com.tsingtec.mini.service.impl;

import com.tsingtec.mini.entity.sys.SysLog;
import com.tsingtec.mini.repository.SysLogRepository;
import com.tsingtec.mini.service.SysLogService;
import com.tsingtec.mini.vo.req.sys.log.SysLogPageReqVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author lj
 * @Date 2020/3/29 13:54
 * @Version 1.0
 */
@Service
public class LogServiceImpl implements SysLogService {

    @Autowired
    private SysLogRepository logRepository;


    @Override
    public Page<SysLog> pageInfo(SysLogPageReqVO vo) {
        Pageable pageable = PageRequest.of(vo.getPageNum()-1, vo.getPageSize(),Sort.Direction.DESC,"id");
        return logRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<Predicate>();

            if (!StringUtils.isEmpty(vo.getOperation())){
                predicates.add(criteriaBuilder.like(root.get("operation"),"%"+vo.getOperation()+"%"));
            }

            if (!StringUtils.isBlank(vo.getUsername())){
                predicates.add(criteriaBuilder.equal(root.get("username"),vo.getUsername()));
            }

            if (null != vo.getAid()){
                predicates.add(criteriaBuilder.equal(root.get("aid"),vo.getAid()));
            }

            if (null != vo.getStartTime()){
                predicates.add(criteriaBuilder.greaterThan(root.get("createTime"),vo.getStartTime()));
            }

            if (null !=vo.getEndTime()){
                predicates.add(criteriaBuilder.lessThan(root.get("createTime"),vo.getEndTime()));
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        },pageable);
    }

    @Override
    @Transactional
    public void insert(SysLog sysLog) {
        logRepository.save(sysLog);
    }

    @Override
    @Transactional
    public void delete(List<Integer> ids) {
        logRepository.deleteBatch(ids);
    }

}
