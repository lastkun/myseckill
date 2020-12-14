package com.ghk.seckill.dao;

import com.ghk.seckill.domian.OrderInfo;
import com.ghk.seckill.domian.SeckillOrder;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderDao {
    @Select("SELECT * FROM SECKILL_ORDER WHERE CUSTOMER_ID = #{customerId} AND GOODS_ID = #{goodsId}")
    SeckillOrder getSeckillOrderById(@Param("customerId") String customerId, @Param("goodsId") String goodsId);

    @Insert("INSERT INTO ORDER_INFO(CUSTOMER_ID,GOODS_ID,GOODS_COUNT,CREATE_TIME,STATUS,GOODS_PRICE,GOODS_NAME) VALUES(" +
            "#{customerId},#{goodsId},#{goodsCount},#{createTime},#{status},#{goodsPrice},#{goodsName})")
    @SelectKey(statement = "select last_insert_id()", keyProperty ="id" , keyColumn = "id" ,before =false , resultType = String.class)
    String insertOrder(OrderInfo orderInfo);

    @Insert("INSERT INTO SECKILL_ORDER(CUSTOMER_ID,GOODS_ID,ORDER_ID) VALUES (#{customerId}," +
            "#{goodsId},#{orderId})")
    int insertSeckillOrder(SeckillOrder seckillOrder);
}
