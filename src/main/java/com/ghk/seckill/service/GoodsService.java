package com.ghk.seckill.service;

import com.ghk.seckill.dao.GoodsDao;
import com.ghk.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService {

    @Autowired(required = false)
    GoodsDao goodsDao;

    public List<GoodsVo> findGoodsListWithSeckill(){
        return goodsDao.findGoodsListWithSeckill();
    }

    public GoodsVo getGoodsVoById(String goodsId) {
        return goodsDao.getGoodsVoById(goodsId);
    }
}
