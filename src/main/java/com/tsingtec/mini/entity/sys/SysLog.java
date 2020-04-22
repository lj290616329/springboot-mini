package com.tsingtec.mini.entity.sys;

import com.tsingtec.mini.entity.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "sys_log")
public class SysLog extends BaseEntity {

    private Integer aid;

    private String username;

    private String operation;

    private Integer time;

    private String method;

    private String params;

    private String ip;

}