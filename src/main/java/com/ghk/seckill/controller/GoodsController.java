package com.ghk.seckill.controller;


import com.ghk.seckill.domian.Customer;
import com.ghk.seckill.service.CustomerService;
import com.ghk.seckill.service.GoodsService;
import com.ghk.seckill.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    CustomerService customerService;

    @Autowired
    GoodsService goodsService;

    /**
     *
     * @param model
     * @param customer  在config.WebConfig里重写了addArgumentResolvers方法，会给customer赋值
     * @return
     */
    @RequestMapping("/to_goods_page")
    public String toPage(Model model, Customer customer){
        model.addAttribute("customer",customer);
        List<GoodsVo> goodsListWithSeckill = goodsService.findGoodsListWithSeckill();
        model.addAttribute("goodsList",goodsListWithSeckill);
        if (customer == null)
            return "login";
        else
            return "goods";
    }


    @RequestMapping("/to_detail_page/{goodsId}")
    public String toDetail(Model model,Customer customer , @PathVariable("goodsId") String goodsId) throws ParseException {
        model.addAttribute("customer",customer);
        if (customer == null)
            return "login";
        GoodsVo goodsVoById = goodsService.getGoodsVoById(goodsId);

        model.addAttribute("goods",goodsVoById);

        //倒计时
        int status = 0;//秒杀状态 0：未开始 1：正在秒杀 2：秒杀结束
        int timeTemp = 0; //显示还剩多少秒开始秒杀
        long startTime =0L;
        long endTime = 0L;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String start = goodsVoById.getStartTime();
        if (start != null && start.length()>0){
            Date startDate = simpleDateFormat.parse(start);
            startTime = startDate.getTime();
        }

        String end = goodsVoById.getEndTime();
        if (end != null && end.length()>0){
            Date endDate = simpleDateFormat.parse(end);
            endTime = endDate.getTime();
        }

        long nowTime = System.currentTimeMillis();
        if (nowTime < startTime){
            timeTemp = (int) ((startTime - nowTime)/1000);
        }else if(nowTime>endTime){
            status = 2;
            timeTemp = -1;
        }else {
            status = 1;
        }

        model.addAttribute("status",status);
        model.addAttribute("remainTime",timeTemp);
        return "goods_detail";

    }
}