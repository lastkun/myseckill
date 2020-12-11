package com.ghk.seckill.vo;

import com.ghk.seckill.validator.CheckMobile;

import javax.validation.constraints.NotNull;

public class LoginVo {

    @NotNull
    @CheckMobile
    private String customerId;

    @NotNull
    private String password;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginVo{" +
                "customerId='" + customerId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
