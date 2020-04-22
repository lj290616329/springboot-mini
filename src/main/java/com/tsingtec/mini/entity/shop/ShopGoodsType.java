package com.tsingtec.mini.entity.shop;

import com.tsingtec.mini.entity.BaseEntity;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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
public class ShopGoodsType extends BaseEntity {

    private Integer gid;//商品id

    private String name;//类型

    private String pic;//图片

    private Integer total=0;//总数

    private Integer limitBuy=0;//限制购买数量

    private Byte status=0;//状态，0为初始值 -1为删除

}
