package com.ghk.seckill.redis;

public abstract class RedisBasePrefix implements RedisKeyPrefix{

    private Integer expireSeconds;

    private String prefix;

    /**
     * 默认为过期时间为0： 永不过期
     */
    public RedisBasePrefix(String prefix) {
        this(0,prefix);
    }

    public RedisBasePrefix(Integer expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    @Override
    public Integer getExpireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String simpleName = getClass().getSimpleName();
        return simpleName.concat(":").concat(prefix);
    }
}