package com.ghk.seckill.rabbitmq;

import com.ghk.seckill.domian.Customer;
import com.ghk.seckill.domian.OrderInfo;
import com.ghk.seckill.domian.SeckillOrder;
import com.ghk.seckill.redis.RedisService;
import com.ghk.seckill.result.CodeMsg;
import com.ghk.seckill.service.GoodsService;
import com.ghk.seckill.service.OrderService;
import com.ghk.seckill.service.SeckillService;
import com.ghk.seckill.utils.BeanAndStringConverter;
import com.ghk.seckill.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQReceiver {
    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    SeckillService seckillService;

    @Autowired
    RedisService redisService;


    private static Logger logger = LoggerFactory.getLogger(MQSender.class);

    @RabbitListener(queues = RabbitConfig.SECKILL_QUEUE)
    public void receive(String msg){
        logger.info("receive message:"+msg);
        SeckillMessage seckillMessage = BeanAndStringConverter.strToBean(msg, SeckillMessage.class);
        Customer customer = seckillMessage.getCustomer();
        String goodsId = seckillMessage.getGoodsId();

        //判断库存
        GoodsVo goods = goodsService.getGoodsVoById(goodsId);
        Integer seckillStock = goods.getSeckillStock();
        if (seckillStock < 1){
            return;
        }
        //判断是否秒杀到
        SeckillOrder seckillOrder = orderService.getSeckillOrder(customer.getCustomerId(),goodsId);
        if (seckillOrder != null){
            return;
        }
        //秒杀到了 减库存 写订单
        seckillService.seckill(customer,goods);
    }
}
