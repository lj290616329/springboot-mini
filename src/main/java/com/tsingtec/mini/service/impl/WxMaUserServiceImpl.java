package com.tsingtec.mini.service.impl;

import com.tsingtec.mini.entity.app.WxMaUser;
import com.tsingtec.mini.repository.WxMaUserRepository;
import com.tsingtec.mini.service.WxMaUserService;
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
public class WxMaUserServiceImpl implements WxMaUserService {

    @Autowired
    private WxMaUserRepository wxMaUserRepository;

    @Override
    public WxMaUser findByOpenId(String openId) {
        return wxMaUserRepository.findByOpenId(openId);
    }

    @Override
    public WxMaUser get(Integer id) {
        return wxMaUserRepository.getOne(id);
    }

    @Override
    public Page<WxMaUser> pageInfo(WxUserPageReqVO vo) {
        Pageable pageable = PageRequest.of(vo.getPageNum()-1, vo.getPageSize(), Sort.Direction.DESC,"id");
        return wxMaUserRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
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
    public WxMaUser insert(WxMaUser wxMaUser) {
        wxMaUser.setCreateTime(new Date());
        wxMaUser.setUpdateTime(new Date());
        return wxMaUserRepository.save(wxMaUser);
    }

    @Override
    public void update(WxMaUser wxMaUser) {
        wxMaUser.setUpdateTime(new Date());
        wxMaUserRepository.save(wxMaUser);
    }

}
