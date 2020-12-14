package com.ghk.seckill.service;

import com.ghk.seckill.dao.GoodsDao;
import com.ghk.seckill.domian.SeckillGoods;
import com.ghk.seckill.domian.SeckillOrder;
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

    public void reduceStock(GoodsVo goods) {
        SeckillGoods seckillGoods = new SeckillGoods();
        seckillGoods.setGoodsId(goods.getId());
        //这里为什么传个秒杀商品对象过去就获取到id了
        goodsDao.reduceStock(seckillGoods);
    }
}
