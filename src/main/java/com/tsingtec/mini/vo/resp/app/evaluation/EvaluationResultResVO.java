package com.tsingtec.mini.vo.resp.app.evaluation;

import lombok.Data;

/**
 * @Author lj
 * @Date 2020/6/29 17:38
 * @Version 1.0
 */
@Data
public class EvaluationResultResVO {
    private String src;
    private Integer id;
    public EvaluationResultResVO(Integer id,String src){
        this.id = id;
        this.src = src;
    }
}
