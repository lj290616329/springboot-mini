package com.tsingtec.mini.entity.mini;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "wx_ma_user")
public class MaUser implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
	private String id;

	@JsonIgnore
	private String openId; //公众号openid
	private String country;//所属国家
	private String province;  //微信资料省份
	private String city;//所属城市
	private String nickName;//昵称
	private String gender;//性别
	private String language;//语言
	private String avatarUrl;//微信图片图标

	@JsonIgnore
	private String unionId; //微信开放平台id
	
	private String phone;//用户电话
	private String name;//用户电话
	
    private Date updateTime;//最后登录时间

	private Date createTime;//创建时间
	
	public String getName() {
		if(StringUtils.isEmpty(name)) {
			return nickName;
		}
		return name;
	}
	
}