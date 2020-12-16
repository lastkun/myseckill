package com.ghk.seckill.redis;

public class ListKey extends RedisBasePrefix {
    private static final int LIST_CACHE_TIME= 60*1000;//毫秒
    private ListKey(String prefix) {
        super(prefix);
    }

    private ListKey(Integer expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static ListKey goodsListKey= new ListKey(LIST_CACHE_TIME,"goodsList");
}
