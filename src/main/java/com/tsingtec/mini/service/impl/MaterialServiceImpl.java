package com.tsingtec.mini.service.impl;

import com.tsingtec.mini.entity.file.Material;
import com.tsingtec.mini.repository.MaterialRepository;
import com.tsingtec.mini.service.MaterialService;
import com.tsingtec.mini.vo.req.material.MaterialPageReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author lj
 * @Date 2020/3/29 13:54
 * @Version 1.0
 */
@Service
public class MaterialServiceImpl implements MaterialService {

    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public void save(Material material) {
        material.setUpdateTime(new Date());
        materialRepository.save(material);
    }

    @Override
    public Page<Material> pageInfo(MaterialPageReqVO vo) {
        Pageable pageable = PageRequest.of(vo.getPageNum()-1, vo.getPageSize(), Sort.Direction.DESC,"id");
        return materialRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<Predicate>();
            predicates.add(criteriaBuilder.equal(root.get("pid"),"%"+vo.getPid()+"%"));

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
    public void deleteBatch(List<Integer> mids) {
        materialRepository.deleteBatch(mids);
    }
}
