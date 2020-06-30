package com.tsingtec.mini.vo.resp.app.mini;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * @Author lj
 * @Date 2020/6/9 16:21
 * @Version 1.0
 */
@Data
public class MiniUserInfoRespVO {

    @JsonProperty("openId")
    private String miniOpenid;

    @JsonProperty("nickName")
    private String nickname;

    @JsonProperty("gender")
    private Integer sex;
    private String language;
    private String city;
    private String province;
    private String country;

    @JsonProperty("avatarUrl")
    private String headImgUrl;
    private String unionId;

}
