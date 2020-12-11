package com.ghk.seckill.redis;


public class CustomerKey extends RedisBasePrefix {
    private static final int TOKEN_EXPIRE_TIME= 600*1000;//毫秒

    private CustomerKey(String prefix) {
        super(prefix);
    }

    private CustomerKey(Integer expireSeconds,String prefix) {
        super(expireSeconds,prefix);
    }

    public static CustomerKey getByCusId = new CustomerKey("id");
    public static CustomerKey getByCusName = new CustomerKey("name");
    public static CustomerKey token = new CustomerKey(TOKEN_EXPIRE_TIME,"token");

}
