package com.tsingtec.mini.service;

import com.tsingtec.mini.entity.mini.Evaluation;
import com.tsingtec.mini.vo.req.mini.doctor.PatientReqVO;
import com.tsingtec.mini.vo.req.mini.evaluation.EvaluationAddReqVO;
import com.tsingtec.mini.vo.req.mini.evaluation.EvaluationUpdateReqVO;
import com.tsingtec.mini.vo.resp.app.doctor.PatientRespVO;
import com.tsingtec.mini.vo.resp.app.evaluation.EvaluationDetailRespVO;
import com.tsingtec.mini.vo.resp.app.evaluation.EvaluationListRespVO;
import com.tsingtec.mini.vo.resp.app.evaluation.EvaluationResultResVO;

import java.util.List;
import java.util.Map;

public interface EvaluationService {

    EvaluationResultResVO save(EvaluationAddReqVO vo);

    void update(EvaluationUpdateReqVO vo);

    List<EvaluationListRespVO> pageInfo(Integer aid);

    EvaluationDetailRespVO detail(Integer id);

    Evaluation getById(Integer id);


    Map<String,List<EvaluationListRespVO>> fingByUid(Integer uid);

    List<PatientRespVO> fingByUidAndName(Integer id, String name);

    Map<String,List<EvaluationListRespVO>> getListByPid(Integer uid, PatientReqVO vo);

    Integer bind(Integer id, Integer valueOf);
}
