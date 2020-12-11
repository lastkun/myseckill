package com.ghk.seckill.controller;

import com.ghk.seckill.redis.RedisService;
import com.ghk.seckill.result.Result;
import com.ghk.seckill.service.CustomerService;
import com.ghk.seckill.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    CustomerService customerService;

    @Autowired
    RedisService redisService;

    @RequestMapping("/to_page")
    public String toPage(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo){

        customerService.login(response,loginVo);

        return Result.success(true);
    }

}
