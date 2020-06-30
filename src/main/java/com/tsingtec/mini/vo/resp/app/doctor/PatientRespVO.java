package com.tsingtec.mini.vo.resp.app.doctor;

import lombok.Data;

/**
 * @Author lj
 * @Date 2020/6/27 18:01
 * @Version 1.0
 */
@Data
public class PatientRespVO {
    private Integer id;//病人uid
    private String name;//姓名
    private String headimgurl;//头像
    private Integer sex;//性别
    private Integer sum;//有几次记录了

    public PatientRespVO(Integer id,String name,String headimgurl,Integer sex,Integer sum){
        this.id = id;
        this.name = name;
        this.headimgurl = headimgurl;
        this.sex = sex;
        this.sum = sum;
    }
}
