package com.tsingtec.mini.service.impl;

import com.tsingtec.mini.entity.mp.MpUser;
import com.tsingtec.mini.exception.BusinessException;
import com.tsingtec.mini.exception.code.BaseExceptionType;
import com.tsingtec.mini.repository.MpUserRepository;
import com.tsingtec.mini.service.MpUserService;
import com.tsingtec.mini.utils.BeanUtil;
import com.tsingtec.mini.vo.req.app.mini.WxUserPageReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MpUserServiceImpl implements MpUserService {

    @Autowired
    private MpUserRepository mpUserRepository;

    @Override
    public MpUser findByOpenId(String openId) {
        if(StringUtils.isEmpty(openId)){
            throw new BusinessException(BaseExceptionType.USER_ERROR,"非法openid");
        }
        return mpUserRepository.findByOpenId(openId);
    }

    @Override
    public MpUser findByUnionId(String unionId) {
        return mpUserRepository.findByUnionId(unionId);
    }

    @Override
    public MpUser get(Integer id) {
        return mpUserRepository.findById(id).orElse(null);
    }

    @Override
    public Page<MpUser> pageInfo(WxUserPageReqVO vo) {
        Pageable pageable = PageRequest.of(vo.getPageNum()-1, vo.getPageSize(), Sort.Direction.DESC,"id");
        return mpUserRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<Predicate>();

            if (!org.apache.commons.lang3.StringUtils.isEmpty(vo.getName())){
                predicates.add(criteriaBuilder.like(root.get("name"),"%"+vo.getName()+"%"));
            }

            if (!org.apache.commons.lang3.StringUtils.isBlank(vo.getNickName())){
                predicates.add(criteriaBuilder.like(root.get("nickName"),"%"+vo.getNickName()+"%"));
            }

            if(!StringUtils.isEmpty(vo.getGender())){
                predicates.add(criteriaBuilder.equal(root.get("gender"),vo.getGender()));
            }
            if(!StringUtils.isEmpty(vo.getPhone())){
                predicates.add(criteriaBuilder.like(root.get("phone"),"%"+vo.getPhone()+"%"));
            }

            if (null != vo.getStartTime()){
                predicates.add(criteriaBuilder.greaterThan(root.get("updateTime"),vo.getStartTime()));
            }

            if (null !=vo.getEndTime()){
                predicates.add(criteriaBuilder.lessThan(root.get("updateTime"),vo.getEndTime()));
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        },pageable);
    }

    @Override
    public MpUser save(MpUser mpUser) {
        MpUser saveUser = findByOpenId(mpUser.getOpenId());
        saveUser = (saveUser != null) ? saveUser : new MpUser();
        BeanUtil.copyPropertiesIgnoreNull(mpUser,saveUser);
        if(saveUser.getCreateTime() == null){
            saveUser.setCreateTime(new Date());
        }
        saveUser.setUpdateTime(new Date());
        return mpUserRepository.save(saveUser);
    }
}
