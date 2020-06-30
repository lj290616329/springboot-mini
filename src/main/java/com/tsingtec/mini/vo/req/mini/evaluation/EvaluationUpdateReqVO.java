package com.tsingtec.mini.vo.req.mini.evaluation;

import com.tsingtec.mini.vo.resp.app.evaluation.ResultRespVO;
import lombok.Data;

import java.util.List;

/**
 * @Author lj
 * @Date 2020/6/23 15:26
 * @Version 1.0
 */
@Data
public class EvaluationUpdateReqVO {
    private Integer id;
    private List<ResultRespVO> vos;
}
