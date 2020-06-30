package com.tsingtec.mini.vo.resp.app.evaluation;

import com.tsingtec.mini.entity.mp.Information;
import lombok.Data;

import java.util.Date;

/**
 * @Author lj
 * @Date 2020/6/23 15:33
 * @Version 1.0
 */
@Data
public class EvaluationListRespVO {
    private Integer id;
    private Integer did;
    private Information information;
    private Date createTime;
    private Date updateTime;
}
