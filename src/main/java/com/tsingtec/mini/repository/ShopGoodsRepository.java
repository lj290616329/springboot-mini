package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.shop.ShopGoods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShopGoodsRepository extends JpaRepository<ShopGoods, Integer>, JpaSpecificationExecutor<ShopGoods> {

}
