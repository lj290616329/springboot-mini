package com.tsingtec.mini.service.impl;

import com.tsingtec.mini.entity.shop.ShopGoods;
import com.tsingtec.mini.repository.ShopGoodsRepository;
import com.tsingtec.mini.service.ShopGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author lj
 * @Date 2020/3/29 13:54
 * @Version 1.0
 */
@Service
public class ShopGoodsServiceImpl implements ShopGoodsService {

    @Autowired
    private ShopGoodsRepository shopGoodsRepository;

    @Override
    public void save(ShopGoods shopGoods) {
        shopGoodsRepository.save(shopGoods);
    }
}
