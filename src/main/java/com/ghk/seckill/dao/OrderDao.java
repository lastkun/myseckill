package com.ghk.seckill.dao;

import com.ghk.seckill.domian.OrderInfo;
import com.ghk.seckill.domian.SeckillOrder;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderDao {
    @Select("SELECT * FROM SECKILL_ORDER WHERE CUSTOMER_ID = #{customerId} AND GOODS_ID = #{goodsId}")
    SeckillOrder getSeckillOrderById(@Param("customerId") String customerId, @Param("goodsId") String goodsId);

    @Insert("INSERT INTO ORDER_INFO(ID,CUSTOMER_ID,GOODS_ID,GOODS_COUNT,CREATE_TIME,STATUS,GOODS_PRICE,GOODS_NAME) VALUES(" +
            "#{id},#{customerId},#{goodsId},#{goodsCount},#{createTime},#{status},#{goodsPrice},#{goodsName})")
    //@SelectKey(statement = "SELECT SEQ_USER_ID.nextval as id from dual", keyProperty ="id" , keyColumn = "id" ,before =true , resultType = String.class)
    int insertOrder(OrderInfo orderInfo);

    @Insert("INSERT INTO SECKILL_ORDER(ID,CUSTOMER_ID,GOODS_ID,ORDER_ID) VALUES (#{id},#{customerId}," +
            "#{goodsId},#{orderId})")
    int insertSeckillOrder(SeckillOrder seckillOrder);
}
