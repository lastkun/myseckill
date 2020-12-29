package com.ghk.seckill.rabbitmq;

import com.ghk.seckill.utils.BeanAndStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQSender {

    private static Logger logger = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    AmqpTemplate amqpTemplate;

    public void sendSeckillMessage(SeckillMessage message){
        String msg = BeanAndStringConverter.beanToStr(message);
        logger.info("seckill send msg:"+msg);
        amqpTemplate.convertAndSend(RabbitConfig.SECKILL_QUEUE,msg);
    }
}
