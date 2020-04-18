package com.tsingtec.mini.repository;

import com.tsingtec.mini.entity.shop.ShopGoodsType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ShopGoodsTypeRepository extends JpaRepository<ShopGoodsType, Integer>, JpaSpecificationExecutor<ShopGoodsType> {

}
