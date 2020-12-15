package com.ghk.seckill.service;

import com.ghk.seckill.dao.OrderDao;
import com.ghk.seckill.domian.Customer;
import com.ghk.seckill.domian.OrderInfo;
import com.ghk.seckill.domian.SeckillOrder;
import com.ghk.seckill.utils.IdGenerator;
import com.ghk.seckill.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class OrderService {
    @Autowired
    IdGenerator idGenerator;

    @Resource
    OrderDao orderDao;

    public SeckillOrder getSeckillOrder(String customerId, String goodsId) {
        return orderDao.getSeckillOrderById(customerId,goodsId);
    }

    @Transactional
    public OrderInfo writeOrder(Customer customer, GoodsVo goods) {
        String orderId = idGenerator.nextId();
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(orderId);
        orderInfo.setCreateTime(new Date().toString());
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        //这里是秒杀价格
        orderInfo.setGoodsPrice(goods.getSeckillPrice());
        orderInfo.setStatus(0);
        orderInfo.setCustomerId(customer.getCustomerId());
        orderDao.insertOrder(orderInfo);
        SeckillOrder seckillOrder = new SeckillOrder();
        seckillOrder.setId(idGenerator.nextId());
        seckillOrder.setCustomerId(customer.getCustomerId());
        seckillOrder.setGoodsId(goods.getId());
        seckillOrder.setOrderId(orderId);
        orderDao.insertSeckillOrder(seckillOrder);
        return orderInfo;
    }
}
