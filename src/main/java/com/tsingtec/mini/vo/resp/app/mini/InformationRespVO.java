package com.tsingtec.mini.vo.resp.app.mini;

import lombok.Data;

import java.util.Date;

/**
 * @Author lj
 * @Date 2020/4/3 17:33
 * @Version 1.0
 */

@Data
public class InformationRespVO {
    private String name;//姓名
    private String phone;//电话
    private Integer sex;//1男2女0未知

    private Date birthday;//生日
    private Integer marry;//1已婚2未婚3离异
    private String profession;//职业
    private String domicile;//籍贯
    private String address;
    private Long age;
}
