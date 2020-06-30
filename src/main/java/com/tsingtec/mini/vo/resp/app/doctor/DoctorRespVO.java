package com.tsingtec.mini.vo.resp.app.doctor;

import com.tsingtec.mini.entity.mp.MpUser;
import lombok.Data;

/**
 * @Author lj
 * @Date 2020/6/27 17:37
 * @Version 1.0
 */
@Data
public class DoctorRespVO {
    private MpUser mpUser;//用户信息
    private Integer aid;
    private String name;//姓名
    private String phone;//电话
    private String goodAt;//擅长
    private String description;//简介
    private String introduce;//具体介绍
}
