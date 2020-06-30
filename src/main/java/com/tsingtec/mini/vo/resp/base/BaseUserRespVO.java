package com.tsingtec.mini.vo.resp.base;

import lombok.Data;

/**
 * @Author lj
 * @Date 2020/6/9 16:06
 * @Version 1.0
 */
@Data
public class BaseUserRespVO {
    private Integer type=1;//1用户2医生
    private Boolean ifAuth;//是否填写个人信息
    private String token;//token


    public BaseUserRespVO(Integer type,Boolean ifAuth,String token){
        this.type = type;
        this.ifAuth = ifAuth;
        this.token = token;
    }
}
