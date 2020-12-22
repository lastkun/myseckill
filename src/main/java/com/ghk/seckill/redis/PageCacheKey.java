package com.ghk.seckill.redis;

import org.apache.commons.lang3.StringUtils;

public class PageCacheKey extends RedisBasePrefix {
    private static final int PAGE_CACHE_TIME = 60*1000;//毫秒
    private PageCacheKey(String prefix) {
        super(prefix);
    }

    private PageCacheKey(Integer expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static PageCacheKey goodsPageCacheKey = new PageCacheKey(PAGE_CACHE_TIME,"goodsList");
    public static PageCacheKey goodsDetailCacheKey = new PageCacheKey(PAGE_CACHE_TIME,"goodsDetail");


}
