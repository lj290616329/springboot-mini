package com.tsingtec.mini.entity.sys;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tsingtec.mini.entity.BaseEntity;
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
public class Menu extends BaseEntity {

    private String name;

    private String icon = "layui-icon-home";

    private String perms;

    private String url;

    private Integer pid;

    private String method;

    @Transient
    private String pidName;

    private Integer orderNum;

    private Integer type;

    private Integer status;

    @ManyToMany(mappedBy = "menus",fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Role> roles = new HashSet<>(0);

    public Menu(){

    }
}