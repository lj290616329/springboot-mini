package com.tsingtec.mini.vo.req.mini.doctor;

import lombok.Data;

/**
 * @Author lj
 * @Date 2020/6/27 17:40
 * @Version 1.0
 */
@Data
public class DoctorReqVO {
    private String name;//姓名
    private String phone;//电话
    private String goodAt;//擅长
    private String description;//简介
    private String introduce;//具体介绍
}
