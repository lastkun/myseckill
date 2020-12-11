package com.ghk.seckill.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


import java.util.List;

@Configuration
public class  WebConfig implements WebMvcConfigurer {
    @Autowired
    CustomerResolver customerResolver;

    /**
     *框架会回调这个方法，往controller里的参数赋值
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(customerResolver);
    }
}
