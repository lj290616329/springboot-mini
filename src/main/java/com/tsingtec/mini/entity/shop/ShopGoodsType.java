package com.tsingtec.mini.entity.shop;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 商品表类型表
 * @Author lj
 * @Date 2020/4/10 16:49
 * @Version 1.0
 */
@Data
@Entity
@DynamicInsert(true)
@DynamicUpdate(true)
@Table(name = "shop_goods_type")
public class ShopGoodsType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator = "JDBC")
    private Integer id;

    private Integer gid;
    private String name;//类型
    private Integer total=0;//总数
    private Integer limitBuy=0;//限制购买数量
}
