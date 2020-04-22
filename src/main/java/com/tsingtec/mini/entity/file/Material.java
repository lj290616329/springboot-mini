package com.tsingtec.mini.entity.file;

import com.tsingtec.mini.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author lj
 * @Date 2020/4/5 22:42
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "file_material")
public class Material extends BaseEntity {

    private String name;//名称

    private Integer type=1;//类型1文件0 文件夹

    private String suffix;//后缀类型

    private String url; //链接

    private Integer pid=0; //上级目录

}
