package com.ghk.seckill.utils;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5Util {
    private static final String salt = "asd1fg";

    public static String hex(String s){
        return DigestUtils.md5Hex(s);
    }

    /**
     * 用户输入密码加盐并做md5加密
     * @return
     */
    public static String encryptionInputPwd(String psw){
        return hex(psw + salt);
    }

    /**
     *密码存到数据库前再做一次加密
     * @return
     */
    public static String encryptionPwdToDatabase(String newPwd,String salt){
        return hex(newPwd + salt);
    }

    public static void main(String[] args) {
        System.out.println(encryptionPwdToDatabase(encryptionInputPwd("123456"),"asd1fg"));
    }
}
