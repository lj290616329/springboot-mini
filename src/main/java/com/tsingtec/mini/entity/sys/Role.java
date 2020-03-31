package com.tsingtec.mini.entity.sys;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.tsingtec.mini.vo.resp.sys.menu.MenuRespNode;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色表
 * @author lj
 *
 */
@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "sys_role")
@ToString(exclude = {"admins", "menus"})
@EqualsAndHashCode(exclude = {"admins", "menus"})
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private Integer id;

	private String name;

    private String description;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "sys_role_menu",joinColumns = @JoinColumn(name = "rid"),inverseJoinColumns = @JoinColumn(name = "mid"))
    @JsonIgnore
    private Set<Menu> menus = new HashSet<>(0);

    @ManyToMany(mappedBy = "roles", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private Set<Admin> admins = new HashSet<>(0);


    @Transient
    private List<MenuRespNode> chilids;

}