package com.ghk.seckill.redis;

/**
 * key前缀接口
 * 接口-抽象类-实现类
 */
public interface RedisKeyPrefix {
    /**
     * 过期时间
     * @return
     */
    Integer getExpireSeconds();

    String getPrefix();

}
