package com.tsingtec.mini.entity.sys;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "sys_menu")
@ToString(exclude = {"roles"})
@EqualsAndHashCode(exclude = {"roles"})
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private Integer id;

    private String name;

    private String icon = "layui-icon-home";

    private String perms;

    private String url;

    private Integer pid;

    @Transient
    private String pidName;

    private Integer orderNum;

    private Integer type;

    private Integer status;

    private Date createTime;

    private Date updateTime;


    @ManyToMany(mappedBy = "menus",fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Role> roles = new HashSet<>(0);

    public Menu(){

    }

    public Menu(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
}