package com.tsingtec.mini.service.impl;

import com.tsingtec.mini.entity.mp.Information;
import com.tsingtec.mini.entity.mp.MpUser;
import com.tsingtec.mini.repository.InformationRepository;
import com.tsingtec.mini.service.InformationService;
import com.tsingtec.mini.utils.BeanMapper;
import com.tsingtec.mini.utils.BeanUtil;
import com.tsingtec.mini.vo.req.mini.information.InformationReqVO;
import com.tsingtec.mini.vo.resp.app.mini.InformationRespVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author lj
 * @Date 2020/6/25 14:48
 * @Version 1.0
 */
@Service
public class InformationServiceImpl implements InformationService {

    @Autowired
    private InformationRepository informationRepository;

    @Override
    @Transactional
    public void save(MpUser user, InformationReqVO vo) {
        Information information = informationRepository.findByMpUser_Id(user.getId());
        if(null==information){
            information = new Information();
        }
        information.setMpUser(user);
        BeanUtil.copyPropertiesIgnoreNull(vo,information);
        informationRepository.save(information);
    }

    @Override
    public InformationRespVO findByUid(Integer uid) {
        Information information = informationRepository.findByMpUser_Id(uid);
        if(null==information){
            return null;
        }
        return BeanMapper.map(information,InformationRespVO.class);
    }

    @Override
    public Boolean ifAuth(Integer uid) {
        Information information = informationRepository.findByMpUser_Id(uid);
        if(null==information){
            return false;
        }
        return true;
    }

    @Override
    public List<Information> getAll() {
        return informationRepository.findAll();
    }

    @Override
    public Information getOne(Integer id) {
        return informationRepository.getOne(id);
    }
}
