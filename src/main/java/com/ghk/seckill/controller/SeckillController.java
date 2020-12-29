package com.ghk.seckill.controller;

import com.ghk.seckill.domian.Customer;
import com.ghk.seckill.domian.Goods;
import com.ghk.seckill.domian.OrderInfo;
import com.ghk.seckill.domian.SeckillOrder;
import com.ghk.seckill.exception.BusinessException;
import com.ghk.seckill.rabbitmq.MQSender;
import com.ghk.seckill.rabbitmq.SeckillMessage;
import com.ghk.seckill.redis.PageCacheKey;
import com.ghk.seckill.redis.RedisService;
import com.ghk.seckill.result.CodeMsg;
import com.ghk.seckill.result.Result;
import com.ghk.seckill.service.GoodsService;
import com.ghk.seckill.service.OrderService;
import com.ghk.seckill.service.SeckillService;
import com.ghk.seckill.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/seckill")
public class SeckillController implements InitializingBean {

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    SeckillService seckillService;

    @Autowired
    RedisService redisService;

    @Autowired
    MQSender sender;


    @RequestMapping("/do_seckill")
    public Result<Integer> seckill(Model model, Customer customer, @RequestParam String goodsId){
        //验证登录
        if (customer == null)
            return Result.fail(CodeMsg.SESSION_ERROR);

        //redis预减库存
        Long stock = redisService.decr(PageCacheKey.goodsSeckillStock, goodsId);
        if (stock<0)
            return Result.fail(CodeMsg.STOCK_ERR);
        //判断是否秒杀到
        SeckillOrder seckillOrder = orderService.getSeckillOrder(customer.getCustomerId(),goodsId);
        if (seckillOrder != null){
            model.addAttribute("errMsg",CodeMsg.EXIST_SECKILL_ORDER.getMsg());
        }
        //如果秒杀到了 入队 （异步下单）
        SeckillMessage message = new SeckillMessage();
        message.setCustomer(customer);
        message.setGoodsId(goodsId);
        sender.sendSeckillMessage(message);
        return Result.success(0);
    }

    /**
     * 实现在系统初始化的时候将秒杀商品库存加载进redis中
     * 使用该方法需要实现InitializingBean接口
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        List<GoodsVo> goodsList= goodsService.findGoodsListWithSeckill();
        if (goodsList == null)
            return;
        for (GoodsVo goodsVo:goodsList){
            redisService.set(PageCacheKey.goodsSeckillStock,goodsVo.getId(),goodsVo.getSeckillStock());
        }
    }
}
