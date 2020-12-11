package com.ghk.seckill.service;

import com.ghk.seckill.dao.CustomerDao;
import com.ghk.seckill.domian.Customer;
import com.ghk.seckill.exception.BusinessException;
import com.ghk.seckill.redis.CustomerKey;
import com.ghk.seckill.redis.RedisService;
import com.ghk.seckill.result.CodeMsg;
import com.ghk.seckill.utils.MD5Util;
import com.ghk.seckill.utils.UUIDUtil;
import com.ghk.seckill.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class CustomerService {

    public static final String COOKIE_TOKEN = "token"; // 记录cookie name 方便取用

    @Autowired(required = false)
    CustomerDao customerDao;

    @Autowired
    RedisService redisService;

    public Customer getCustomerById(String id){
        return customerDao.getCustomerById(id);
    }

    public boolean login(HttpServletResponse response,LoginVo loginVo) {
        if (loginVo == null)
            throw new BusinessException(CodeMsg.ERROR_SERVER);
        String customerId = loginVo.getCustomerId();
        String password = loginVo.getPassword();
        //判断用户名(手机号)是否存在
        Customer customer = getCustomerById(customerId);
        if (customer == null)
            throw new BusinessException(CodeMsg.CUSTOMER_NOT_EXIST);
        String dbPassword = customer.getPassword();

        String salt = customer.getSalt();

        String checkPwd = MD5Util.encryptionPwdToDatabase(password, salt);

        if (!checkPwd.equals(dbPassword)){
            throw new BusinessException(CodeMsg.ERROR_PASSWORD);
        }
        String token = UUIDUtil.getUUID();
        addCookie(customer,token,response);
        return true;
    }

    public void addCookie(Customer customer,String token,HttpServletResponse response){
        //实现session：登录成功后，生成token来标识用户，写到cookie中，并传到客户端，客户端在随后的访问中都在cookie中传入这个token，服务端拿到token后根据这个token获取对应的session信息
        //为了实现分布式session 这里用redis来管理session信息
        redisService.set(CustomerKey.token,token,customer);//标识token对应的哪个客户，将信息存到redis中
        Cookie cookie = new Cookie(COOKIE_TOKEN,token);
        cookie.setMaxAge(CustomerKey.token.getExpireSeconds());//设置过期时间
        cookie.setPath("/");
        response.addCookie(cookie);//cookie写到客户端
    }

    public Customer getCustomerByToken(String token,HttpServletResponse response) {
        if (token == null)
            return null;
        Customer customer = redisService.get(CustomerKey.token, token, Customer.class);
        if (customer != null)
            addCookie(customer,token,response);//为了刷新cookie的有效时间，重新传入cookie
        return customer;
    }
}
