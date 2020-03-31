package com.tsingtec.mini.entity.sys;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


/**
 * 用户账号表
 * @author lj
 *
 */
@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "sys_admin")
@ToString(exclude = {"roles"})
@EqualsAndHashCode(exclude = {"roles"})
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private Integer id;

    //0:禁止登录
    public static final Short UNVALID = -1;
    //1:有效
    public static final Short VALID = 0;

    /**名称*/
    private String name;

    /**登录帐号*/
    private String loginName;

    /**密码*/
    private String password;

    /**登录帐号*/
    private Integer unionId=0;

    /**盐*/
    private String salt;

    /**创建时间*/
    private Date createTime;

    /**最后登录时间*/
    private Date updateTime;

    /**0:有效，-1:禁止登录*/
    private Short status = VALID;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "sys_admin_role",
            joinColumns = @JoinColumn(name = "aid"),
            inverseJoinColumns = @JoinColumn(name = "rid"))
    @JsonIgnore
    private Set<Role> roles = new HashSet<>(0);

}
