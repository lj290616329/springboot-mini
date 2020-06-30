package com.tsingtec.mini.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.google.common.collect.Lists;
import com.tsingtec.mini.config.jwt.JwtUtil;
import com.tsingtec.mini.entity.mini.Doctor;
import com.tsingtec.mini.entity.mini.Evaluation;
import com.tsingtec.mini.entity.mp.Information;
import com.tsingtec.mini.exception.BusinessException;
import com.tsingtec.mini.exception.code.BaseExceptionType;
import com.tsingtec.mini.repository.DoctorRepository;
import com.tsingtec.mini.repository.EvaluationRepository;
import com.tsingtec.mini.repository.InformationRepository;
import com.tsingtec.mini.service.EvaluationService;
import com.tsingtec.mini.utils.BeanMapper;
import com.tsingtec.mini.utils.QrCodeUtil;
import com.tsingtec.mini.vo.req.mini.doctor.PatientReqVO;
import com.tsingtec.mini.vo.req.mini.evaluation.EvaluationAddReqVO;
import com.tsingtec.mini.vo.req.mini.evaluation.EvaluationUpdateReqVO;
import com.tsingtec.mini.vo.resp.app.doctor.PatientRespVO;
import com.tsingtec.mini.vo.resp.app.evaluation.EvaluationDetailRespVO;
import com.tsingtec.mini.vo.resp.app.evaluation.EvaluationListRespVO;
import com.tsingtec.mini.vo.resp.app.evaluation.EvaluationResultResVO;
import com.tsingtec.mini.vo.resp.app.evaluation.ResultRespVO;
import com.tsingtec.mini.vo.resp.app.question.QuestionRespVO;
import com.tsingtec.mini.vo.resp.app.question.QuestionResultListRespVO;
import com.vip.vjtools.vjkit.mapper.JsonMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author lj
 * @Date 2020/6/23 15:52
 * @Version 1.0
 */
@Service
public class EvaluationServiceImpl implements EvaluationService {

    private final JsonMapper mapper = JsonMapper.nonEmptyMapper();

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private InformationRepository informationRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public EvaluationResultResVO save(EvaluationAddReqVO vo){
        Evaluation evaluation = new Evaluation();
        Information information = informationRepository.findByMpUser_Id(vo.getMpUser().getId());
        if(null==information){
            throw new BusinessException(BaseExceptionType.MINI_ERROR,"您的个人信息不存在,请先提交个人信息");
        }
        evaluation.setContent(mapper.toJson(vo.getVos()));
        evaluation.setInformation(information);
        evaluationRepository.save(evaluation);
        String src = "";
        try {
            src = QrCodeUtil.getQRCodeImage(jwtUtil.createToken("qrCode",evaluation.getId().toString()),200,200);
        } catch (IOException e) {
            System.out.println(e.toString());
            throw new BusinessException(BaseExceptionType.MINI_ERROR,"保存失败,请稍后再试");
        }
        return new EvaluationResultResVO(evaluation.getId(),src);
    }

    @Override
    public void update(EvaluationUpdateReqVO vo) {
        Evaluation evaluation = evaluationRepository.getOne(vo.getId());
        evaluation.setResult(mapper.toJson(vo.getVos()));
        evaluationRepository.save(evaluation);
    }

    @Override
    public List<EvaluationListRespVO> pageInfo(Integer did) {
        List<Evaluation> evaluations = evaluationRepository.findByDid(did);
        return BeanMapper.mapList(evaluations,EvaluationListRespVO.class);
    }

    @Override
    public EvaluationDetailRespVO detail(Integer id) {
        EvaluationDetailRespVO respVO = new EvaluationDetailRespVO();
        Evaluation evaluation = evaluationRepository.getOne(id);
        if(null==evaluation){
            throw new BusinessException(BaseExceptionType.MINI_ERROR,"不存在的信息!");
        }
        String src = "";
        try {
            src = QrCodeUtil.getQRCodeImage(jwtUtil.createToken("qrCode",evaluation.getId().toString()),200,200);
        } catch (IOException e) {
            throw new BusinessException(BaseExceptionType.MINI_ERROR,"信息获取失败,请稍后再试");
        }
        respVO.setSrc(src);
        respVO.setInformation(evaluation.getInformation());
        if(evaluation.getDid()!=null){
            Doctor doctor = doctorRepository.getOne(evaluation.getDid());
            respVO.setDoctor(doctor);
        }
        List<QuestionRespVO> questionRespVOS = JSONArray.parseArray(evaluation.getContent(),QuestionRespVO.class);

        List<QuestionResultListRespVO> questionListRespVOS = Lists.newArrayList();
        Map<String,List<QuestionRespVO>> listMap = questionRespVOS.stream().collect(Collectors.groupingBy(s -> s.getGroupName()));
        listMap.forEach((k, v) -> questionListRespVOS.add(new QuestionResultListRespVO(k,v)));
        respVO.setContents(questionListRespVOS);

        if(StringUtils.isNotBlank(evaluation.getResult())){
            List<ResultRespVO> resultRespVOS = JSONArray.parseArray(evaluation.getResult(),ResultRespVO.class);
            respVO.setResults(resultRespVOS);
        }
        return respVO;
    }

    @Override
    public Evaluation getById(Integer id) {
        return evaluationRepository.getOne(id);
    }

    @Override
    public Map<String, List<EvaluationListRespVO>> fingByUid(Integer uid) {
        List<Evaluation> evaluations = evaluationRepository.findByUid(uid);
        List<EvaluationListRespVO> evaluationListRespVOS = BeanMapper.mapList(evaluations,EvaluationListRespVO.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return evaluationListRespVOS.stream().collect(Collectors.groupingBy(s -> sdf.format(s.getCreateTime())));
    }

    @Override
    public List<PatientRespVO> fingByUidAndName(Integer uid, String name) {
        Doctor doctor = doctorRepository.findByMpUser_Id(uid);
        List<Evaluation> evaluations = evaluationRepository.findByDid(doctor.getId());
        List<PatientRespVO> vos = Lists.newArrayList();
        Map<Information, List<Evaluation>> map = evaluations.stream().filter(evaluation -> evaluation.getInformation().getName().indexOf(name)>-1) //筛选出大于150的
                .collect(Collectors.groupingBy(e->e.getInformation()));
        map.forEach((k,v)->{
            vos.add(new PatientRespVO(k.getId(),k.getName(),k.getMpUser().getHeadImgUrl(),k.getSex(),v.size()));
        });
        return vos;
    }

    /**
     * 根据医生用户id 根据条件查询病人病历
     * @param uid
     * @param vo
     * @return
     */
    @Override
    public Map<String, List<EvaluationListRespVO>> getListByPid(Integer uid, PatientReqVO vo) {
        Doctor doctor = doctorRepository.findByMpUser_Id(uid);
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        List<Evaluation> evaluations = evaluationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<Predicate>();
            predicates.add(criteriaBuilder.equal(root.get("did"),doctor.getId()));
            if (vo.getIfEnd()){
                predicates.add(criteriaBuilder.isNull(root.get("result")));
            }
            if (null != vo.getPid()){
                predicates.add(criteriaBuilder.equal(root.get("information").get("id"),vo.getPid()));
            }
            return criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()])).getRestriction();
        },sort);
        List<EvaluationListRespVO> evaluationListRespVOS = BeanMapper.mapList(evaluations,EvaluationListRespVO.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        return evaluationListRespVOS.stream().collect(Collectors.groupingBy(s -> sdf.format(s.getCreateTime())));
    }

    @Override
    public Integer bind(Integer uid, Integer id) {
        Doctor doctor = doctorRepository.findByMpUser_Id(uid);
        if(null==doctor){
            throw new BusinessException(BaseExceptionType.MINI_ERROR,"您不是医生,无权进行此操作!");
        }
        Evaluation evaluation = evaluationRepository.getOne(id);
        if(null==evaluation){
            throw new BusinessException(BaseExceptionType.MINI_ERROR,"不存在的信息!");
        }
        if(evaluation.getDid()!=null && evaluation.getDid()!=doctor.getId()){
            throw new BusinessException(BaseExceptionType.MINI_ERROR,"您没有权限进行访问");
        }
        evaluation.setDid(doctor.getId());
        evaluationRepository.save(evaluation);
        return evaluation.getId();
    }
}
