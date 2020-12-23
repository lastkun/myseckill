package com.ghk.seckill.redis;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;

    /**
     * 根据key获取对应的value
     */
    public <T> T get(RedisKeyPrefix prefix,String key, Class<T> clazz){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String newKey = prefix.getPrefix()+key;
            String s = jedis.get(newKey);
            T t = strToBean(s, clazz);
            return t;
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }

    }

    /**
     * 设置单个k-v
     * @return T/F
     */
    public <T> boolean set(RedisKeyPrefix prefix,String key,T value){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String s = beanToStr(value);
            if(s == null || s.length()<=0){
                return false;
            }
            String newKey = prefix.getPrefix()+key;
            Integer expireSeconds = prefix.getExpireSeconds();
            if (expireSeconds <= 0L){
                jedis.set(newKey,s);
            }else {
                jedis.psetex(newKey,expireSeconds,s);
            }

            return true;
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }

    }

    /**
     * 判断key是否存在
     * @return  T/F
     */
    public <T> boolean exists(RedisKeyPrefix prefix,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String newKey = prefix.getPrefix()+key;
            return jedis.exists(newKey);
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 根据key 增加值
     */
    public <T> Long incr(RedisKeyPrefix prefix,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String newKey = prefix.getPrefix()+key;
            return jedis.incr(newKey);
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 根据key 减小值
     */
    public <T> Long decr(RedisKeyPrefix prefix,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String newKey = prefix.getPrefix()+key;
            return jedis.decr(newKey);
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    /**
     * 删除
     * @param prefix
     * @param key
     * @return
     */
    public boolean delete(RedisKeyPrefix prefix,String key){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String newKey = prefix.getPrefix()+key;
            return jedis.del(newKey)>0;
        }finally {
            if (jedis != null){
                jedis.close();
            }
        }
    }

    private <T> String beanToStr(T value) {
        if(value == null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class){
            return ""+value;
        }else if(clazz == String.class){
            return (String) value;
        }else if(clazz == long.class || clazz == Long.class){
            return ""+value;
        }else {
            return JSON.toJSONString(value);
        }

    }

    private <T> T strToBean(String str,Class<T> clazz){
        if (str == null || str.length() <= 0 || clazz == null){
            return null;
        }
        if (clazz == int.class || clazz == Integer.class){
            return (T) Integer.valueOf(str);
        }else if(clazz == String.class){
            return (T) str;
        }else if(clazz == long.class || clazz == Long.class){
            return (T) Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str),clazz);
        }
    }
}
