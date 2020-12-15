package com.ghk.seckill.dao;

import com.ghk.seckill.domian.Goods;
import com.ghk.seckill.domian.SeckillGoods;
import com.ghk.seckill.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsDao {

    @Select("SELECT G.*,SG.SECKILL_PRICE,SG.START_TIME,SG.END_TIME,SG.SECKILL_STOCK FROM GOODS G LEFT JOIN SECKILL_GOODS SG ON G.ID = SG.ID")
    List<GoodsVo> findGoodsListWithSeckill();

    @Select("SELECT G.*,SG.SECKILL_PRICE,SG.START_TIME,SG.END_TIME,SG.SECKILL_STOCK FROM GOODS G LEFT JOIN SECKILL_GOODS SG ON G.ID = SG.ID WHERE G.ID=#{goodsId}")
    GoodsVo getGoodsVoById(@Param("goodsId") String goodsId);

    @Update("UPDATE SECKILL_GOODS SET SECKILL_STOCK = SECKILL_STOCK-1 WHERE GOODS_ID = #{goodsId} ")
    int reduceStock(SeckillGoods seckillGoods);
}
