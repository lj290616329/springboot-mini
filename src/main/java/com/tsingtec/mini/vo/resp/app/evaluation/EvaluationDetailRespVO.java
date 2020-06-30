package com.tsingtec.mini.vo.resp.app.evaluation;

import com.tsingtec.mini.entity.mini.Doctor;
import com.tsingtec.mini.entity.mp.Information;
import com.tsingtec.mini.vo.resp.app.question.QuestionResultListRespVO;
import lombok.Data;

import java.util.List;

/**
 * @Author lj
 * @Date 2020/6/26 16:14
 * @Version 1.0
 */
@Data
public class EvaluationDetailRespVO {
    private Doctor doctor;
    private Information information;
    private List<ResultRespVO> results;
    private List<QuestionResultListRespVO> contents;
    private String src;
}
