package com.ghk.seckill.result;

public class CodeMsg {
    private int code;
    private String msg;

    public static CodeMsg SUCCESS = new CodeMsg(0,"success");
    public static CodeMsg ERROR_SERVER = new CodeMsg(1001,"服务端异常！");
    public static CodeMsg BIND_EXCEPTION = new CodeMsg(1002,    "校验未通过!");
    //登录
    public static CodeMsg ERROR_PASSWORD = new CodeMsg(2002,"密码不正确，请重新输入!");
    public static CodeMsg CUSTOMER_NOT_EXIST = new CodeMsg(2003,"不存在该用户，请检查用户名，或者立即注册!");
    //秒杀
    public static CodeMsg STOCK_ERR = new CodeMsg(3001,"秒杀库存不足!");
    public static CodeMsg EXIST_SECKILL_ORDER = new CodeMsg(3001,"该用户已参与活动!");
    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
