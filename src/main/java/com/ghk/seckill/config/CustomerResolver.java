package com.ghk.seckill.config;

import com.ghk.seckill.domian.Customer;
import com.ghk.seckill.service.CustomerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class CustomerResolver implements HandlerMethodArgumentResolver {

    @Autowired
    CustomerService customerService;

    /**
     *判断是否要做处理，只有返回true才会做处理
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> parameterType = methodParameter.getParameterType();
        return Customer.class == parameterType;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest nativeRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse nativeResponse = nativeWebRequest.getNativeResponse(HttpServletResponse.class);

        String parameter = nativeRequest.getParameter(CustomerService.COOKIE_TOKEN);
        String cookie= getCookieValue(nativeRequest, CustomerService.COOKIE_TOKEN);

        if (StringUtils.isEmpty(cookie) && StringUtils.isEmpty(parameter))
            return null;
        String token = StringUtils.isEmpty(parameter) ? cookie : parameter;

        return customerService.getCustomerByToken(token,nativeResponse);
    }


    private String getCookieValue(HttpServletRequest request,String cookieName){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie :cookies){
            if (cookie.getName().equals(cookieName))
                return cookie.getValue();
        }
        return null;
    }
}
