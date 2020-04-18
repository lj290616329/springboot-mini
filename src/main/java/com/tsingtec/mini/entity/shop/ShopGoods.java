package com.tsingtec.mini.entity.shop;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 商品表
 * @Author lj
 * @Date 2020/4/10 16:49
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "shop_goods")
public class ShopGoods implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private Integer id;
    private String name;//商品名称
    private String unit;//单位
    private String description;//描述
    private String content;//具体内容
    private BigDecimal originalPrice;//原价
    private BigDecimal presentPrice;//现价
    private String pics;//详情图
    private Short status=0;//状态，0为初始值 -1为删除 1为组团成功 2为组团结束 3为组团失败
    private Date createTime;//创建时间
    private Date updateTime;//更新时间

    @OneToMany(targetEntity = ShopGoodsType.class, cascade=CascadeType.ALL)
    @JoinColumn(name="gid")
    private List<ShopGoodsType> types;

    @Transient
    private String pic;//标题图片

    public String getPic(){
        if(StringUtils.isNotBlank(pics)){
            return pics.split(",")[0];
        }else{
            return "";
        }
    }
}
