package com.ghk.seckill.rabbitmq;

import com.ghk.seckill.domian.Customer;

/**
 * 秒杀message对象
 */
public class SeckillMessage {
    private Customer customer;
    private String goodsId;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }
}
