package com.tsingtec.mini.vo.req.mini.doctor;

import lombok.Data;

/**
 * @Author lj
 * @Date 2020/6/27 23:00
 * @Version 1.0
 */
@Data
public class PatientReqVO {
    private Boolean ifEnd=false;//是否未完成
    private Integer pid;//病人information_id
}
