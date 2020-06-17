package com.tsingtec.mini.vo.resp.base;

import lombok.Data;

/**
 * @Author lj
 * @Date 2020/6/9 16:06
 * @Version 1.0
 */
@Data
public class BaseUserRespVO {
    private String name;//姓名
    private String headImgUrl;//头像
    private String token;//token
}
