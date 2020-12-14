package com.ghk.seckill.service;


import com.ghk.seckill.domian.Customer;
import com.ghk.seckill.domian.OrderInfo;
import com.ghk.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
public class SeckillService {
    //这里不推荐直接引用其他的dao 建议引入service
    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo seckill(Customer customer, GoodsVo goods) {
        //1.减库存
        goodsService.reduceStock(goods);
        //2.写订单 返回订单详情
        return orderService.writeOrder(customer,goods);
    }
}
