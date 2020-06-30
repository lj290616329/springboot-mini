package com.tsingtec.mini.entity.mp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tsingtec.mini.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @Author lj
 * @Date 2020/6/25 12:48
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "wx_information")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Information extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="uid",columnDefinition ="用户id")//指定外键名称
    private MpUser mpUser;

    private String phone;
    private String name;
    private Integer sex;//1男2女0未知
    private Date birthday;//生日
    private Integer marry;//1已婚2未婚3离异
    private String profession;//职业
    private String domicile;//籍贯
    private String address;

    @Transient
    private Long age;

    public Long getAge(){
        LocalDate today = LocalDate.now();
        Instant instant = birthday.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDate localDate = localDateTime.toLocalDate();
        return ChronoUnit.YEARS.between(localDate, today);
    }
}
