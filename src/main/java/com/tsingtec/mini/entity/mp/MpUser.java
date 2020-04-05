package com.tsingtec.mini.entity.mp;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author lj
 * @Date 2020/4/4 22:48
 * @Version 1.0
 */

@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "wx_mp_user")
public class MpUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private Integer id;

    private Boolean subscribe;

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

    private String unionId;

    private String remark;

    private Integer groupId;

    private String subscribeScene;

    private String qrScene;

    private String qrSceneStr;

    private String name;

    private String phone;

    private Date createTime;

    private Date updateTime;

    public String getName() {
        if(StringUtils.isEmpty(name)) {
            return nickname;
        }
        return name;
    }
}
