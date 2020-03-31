package com.tsingtec.mini.entity.sys;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "sys_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private Integer id;

    private Integer aid;

    private String username;

    private String operation;

    private Integer time;

    private String method;

    private String params;

    private String ip;

    private Date createTime;

}