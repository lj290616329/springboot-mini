package com.tsingtec.mini.vo.resp.information;

import com.tsingtec.mini.entity.mp.MpUser;
import lombok.Data;

import java.util.Date;

/**
 * @Author lj
 * @Date 2020/6/25 13:33
 * @Version 1.0
 */
@Data
public class InformationRespVO {
    private MpUser mpUser;
    private String phone;
    private String name;
    private Integer sex;//1男2女0未知
    private Date birthday;//生日
    private Integer marry;//1已婚2未婚3离异
    private String profession;//职业
    private String domicile;//籍贯
    private String address;
    private Long age;
}
