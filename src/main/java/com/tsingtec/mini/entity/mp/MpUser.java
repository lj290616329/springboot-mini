package com.tsingtec.mini.entity.mp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tsingtec.mini.entity.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Author lj
 * @Date 2020/4/4 22:48
 * @Version 1.0
 */

@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "wx_common_user")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class MpUser extends BaseEntity {

    private Boolean subscribe;

    @JsonIgnore
    private String openId;

    private String nickname;

    private String sexDesc;

    private Integer sex;

    private String language;

    private String city;

    private String province;

    private String country;

    private String headImgUrl;

    private Long subscribeTime;

    @JsonIgnore
    @Column(unique = true)
    private String unionId;

    private String remark;

    private Integer groupId;

    private String subscribeScene;

    private String qrScene;

    private String qrSceneStr;

    @JsonIgnore
    private String miniOpenid;//小程序openid

    private String name;

    private String phone;

    public String getName() {
        if(StringUtils.isEmpty(name)) {
            return nickname;
        }
        return name;
    }
}
