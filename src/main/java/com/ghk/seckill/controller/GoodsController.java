package com.ghk.seckill.controller;


import com.ghk.seckill.domian.Customer;
import com.ghk.seckill.redis.PageCacheKey;
import com.ghk.seckill.redis.RedisService;
import com.ghk.seckill.service.CustomerService;
import com.ghk.seckill.service.GoodsService;
import com.ghk.seckill.vo.GoodsVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;

import javax.servlet.http.HttpServletRequest;
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

    @Autowired
    RedisService redisService;

    @Autowired
    ThymeleafViewResolver resolver;


    /**
     * String类的isEmpty()方法判断字符串长度是否为0，StringUtils类则先判断是否为空，再判断长度是否为0
     * thymeleaf.spring5的API中把大部分的功能移到了IWebContext下面,用来区分边界。
     * 剔除了ApplicationContext 过多的依赖，现在thymeleaf渲染不再过多依赖spring容器
     * @ RequestMapping注解的produces属性指定返回数据的类型和编码，一般和@ResponseBody注解配合使用
     * @param model
     * @param customer  在config.WebConfig里重写了addArgumentResolvers方法，会给customer赋值
     * @return
     */
    @RequestMapping(value = "/to_goods_page",produces = "text/html")
    @ResponseBody
    public String toPage(HttpServletRequest request,HttpServletResponse response,Model model, Customer customer){
        // ? 是否要判断customer !=null
        model.addAttribute("customer",customer);

        //判断redis是否已经缓存了页面
        String page = redisService.get(PageCacheKey.goodsPageCacheKey, "page", String.class);
        //如果存在 返回缓存中的页面
        if (!StringUtils.isEmpty(page)){
            return page;
        }

        List<GoodsVo> goodsListWithSeckill = goodsService.findGoodsListWithSeckill();
        model.addAttribute("goodsList",goodsListWithSeckill);

        //如果不存在 手动渲染
        // 将页面信息存入缓存thymeleafViewResolver.getTemplateEngine().process("模板名称",context)
        IWebContext context = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        page = resolver.getTemplateEngine().process("goods", context);
        if (!StringUtils.isEmpty(page)){
            redisService.set(PageCacheKey.goodsPageCacheKey,"",page);
        }
        return page;
    }


    @RequestMapping(value = "/to_detail_page/{goodsId}" , produces = "text/html")
    @ResponseBody
    public String toDetail(HttpServletRequest request,HttpServletResponse response,Model model, Customer customer , @PathVariable("goodsId") String goodsId) throws ParseException {
        model.addAttribute("customer",customer);

        String page = redisService.get(PageCacheKey.goodsDetailCacheKey, goodsId, String.class);
        if (!StringUtils.isEmpty(page)){
            return page;
        }

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

        IWebContext context = new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        page = resolver.getTemplateEngine().process("goods_detail",context);
        if (!StringUtils.isEmpty(page)){
            redisService.set(PageCacheKey.goodsDetailCacheKey,goodsId,page);
        }

        return page;
    }
}